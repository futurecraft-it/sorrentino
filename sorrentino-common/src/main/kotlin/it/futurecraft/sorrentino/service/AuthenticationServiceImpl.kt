package it.futurecraft.sorrentino.service

import com.github.twitch4j.auth.domain.TwitchScopes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import it.futurecraft.sorrentino.auth.Identity
import it.futurecraft.sorrentino.auth.flow.DeviceFlowController
import it.futurecraft.sorrentino.auth.flow.Flow
import it.futurecraft.sorrentino.database.credential.Credential
import it.futurecraft.sorrentino.database.credential.CredentialTable
import it.futurecraft.sorrentino.event.EventBus
import it.futurecraft.sorrentino.utils.wrapper.SchedulerWrapper
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import net.kyori.adventure.audience.Audience
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

private typealias AudienceIdentity = net.kyori.adventure.identity.Identity

class AuthenticationServiceImpl(
    private val _identity: Identity,
    private val _scheduler: SchedulerWrapper,
    private val _eventbus: EventBus
) : AuthenticationService {
    private val _deviceController = DeviceFlowController(_identity, _scheduler, _eventbus)

    private val _client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { isLenient = true; ignoreUnknownKeys = true })
        }
    }

    override suspend fun authenticate(
        target: Audience,
        scopes: List<TwitchScopes>,
        flow: Flow
    ) = when (flow) {
        Flow.DEVICECODE -> _deviceController.start(target, scopes)
    }

    override suspend fun verify(target: Audience): Boolean {
        val uuid = target.getOrDefault(AudienceIdentity.UUID, null) ?: return false
        val credential = transaction { Credential.find(CredentialTable.userId eq uuid).firstOrNull() } ?: return false

        return credential.expiresIn > 0
    }

    override suspend fun refresh(target: Audience): Boolean {
        val uuid = target.getOrDefault(AudienceIdentity.UUID, null) ?: return false
        val credential = transaction { Credential.find(CredentialTable.userId eq uuid).firstOrNull() } ?: return false

        val res = _client.submitForm("${AuthenticationService.ENDPOINT}/token", formParameters = parameters {
            append("client_id", _identity.id)
            append("client_secret", _identity.secret)
            append("grant_type", "refresh_token")
            append("refresh_token", credential.refresh)
        })

        if (res.status.isSuccess()) {
            val data = res.body<DeviceFlowController.Data>()

            credential.access = data.access
            credential.refresh = data.refresh
            credential.expiresAt = Clock.System.now()
                .plus(data.expiration.seconds)
                .toLocalDateTime(TimeZone.UTC)

            return credential.flush()
        }

        return false
    }
}