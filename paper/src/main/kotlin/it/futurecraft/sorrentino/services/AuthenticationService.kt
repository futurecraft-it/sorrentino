package it.futurecraft.sorrentino.services

import com.github.twitch4j.auth.domain.TwitchScopes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.submitForm
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import it.futurecraft.sorrentino.auth.Credentials
import it.futurecraft.sorrentino.auth.Identity
import it.futurecraft.sorrentino.auth.controllers.DeviceFlowController
import it.futurecraft.sorrentino.database.credentials.CredentialsEntity
import it.futurecraft.sorrentino.database.credentials.CredentialsTable
import it.futurecraft.sorrentino.database.user.UserEntity
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player
import org.jetbrains.exposed.v1.core.and
import kotlin.time.Clock

class AuthenticationServiceImpl(private val _identity: Identity) : AuthenticationService {
    private val _controller = DeviceFlowController(_identity)

    override suspend fun authenticate(key: Key, player: Player, scopes: List<TwitchScopes>) {
        _controller.start(key, player, scopes)
    }

    override suspend fun verify(key: Key, player: Player): Boolean {
        // Checks whether the user has ever authenticated before
        val user = UserEntity.findById(player.uniqueId) ?: return false

        // Checks whether the user has credentials for the given key
        val credentials =
            CredentialsEntity.find { (CredentialsTable.userId eq user.id) and (CredentialsTable.key eq key) }
                .firstOrNull() ?: return false

        // Checks whether the credentials are still valid
        return Clock.System.now() < credentials.expiresAt.toInstant(TimeZone.currentSystemDefault())
    }

    override suspend fun refresh(key: Key, player: Player): Boolean {
        val user = UserEntity.findById(player.uniqueId) ?: return false
        val credentials = CredentialsEntity.find { (CredentialsTable.userId eq user.id) and (CredentialsTable.key eq key) }
            .firstOrNull() ?: return false

        val client = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json { prettyPrint = true; isLenient = true; ignoreUnknownKeys = true })
            }
        }

        val res = client.submitForm (
            url = "${AuthenticationService.BASE_URL}/token", formParameters = io.ktor.http.parameters {
                append("client_id", _identity.id)
                append("client_secret", _identity.secret)
                append("grant_type", "refresh_token")
                append("refresh_token", credentials.refresh)
            }
        )

        if (res.status.isSuccess()) {
            val data: Credentials = res.body()

            credentials.access = data.access
            credentials.refresh = data.refresh

            val now = Clock.System.now()
            credentials.expiresAt = now.plus(data.expiration, DateTimeUnit.SECOND)
                .toLocalDateTime(TimeZone.currentSystemDefault())

            return credentials.flush()
        }

        return false
    }
}