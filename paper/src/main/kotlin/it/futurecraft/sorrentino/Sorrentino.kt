package it.futurecraft.sorrentino

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import it.futurecraft.sorrentino.auth.Identity
import it.futurecraft.sorrentino.database.credentials.CredentialsEntity
import it.futurecraft.sorrentino.database.credentials.CredentialsTable
import it.futurecraft.sorrentino.database.user.UserEntity
import it.futurecraft.sorrentino.services.AuthenticationService
import it.futurecraft.sorrentino.services.AuthenticationServiceImpl
import it.futurecraft.sorrentino.services.SubscriptionService
import it.futurecraft.sorrentino.services.SubscriptionServiceImpl
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player
import org.jetbrains.exposed.v1.core.and
import org.koin.core.component.KoinComponent
import kotlin.time.Clock

class SorrentinoImpl : Sorrentino, KoinComponent {
    private val _identity = Identity("nk8kl64j7bik05uiu3b9jtrnwqhgte", "")

    private val _key = Key.key("sorrentino", "main")

    override fun client(): TwitchClient = TwitchClientBuilder.builder()
        .withClientId(_identity.id)
        .withClientSecret(_identity.secret)
        .withEnableHelix(true)
        .build()

    override fun client(identity: Identity): TwitchClient = TwitchClientBuilder.builder()
        .withClientId(identity.id)
        .withClientSecret(identity.secret)
        .withEnableHelix(true)
        .build()

    override suspend fun client(player: Player): TwitchClient = client(_identity, player, _key)

    override suspend fun client(player: Player, key: Key): TwitchClient = client(_identity, player, key)

    override suspend fun client(identity: Identity, player: Player, key: Key): TwitchClient {
        val builder = TwitchClientBuilder.builder()
            .withClientId(identity.id)
            .withClientSecret(identity.secret)
            .withEnableHelix(true)

        val user = UserEntity.findById(player.uniqueId)
            ?: throw IllegalArgumentException("Player ${player.name} has never authenticated.")

        val credentials =
            CredentialsEntity.find { (CredentialsTable.userId eq user.id) and (CredentialsTable.key eq key) }
                .firstOrNull()
                ?: throw IllegalArgumentException("Player ${player.name} has never authenticated with key $key.")

        if (credentials.expiresAt.toInstant(TimeZone.currentSystemDefault()) > Clock.System.now()) {
            if (auth.refresh(key, player)) {
                credentials.refresh()
            } else {
                throw IllegalArgumentException("Player ${player.name} needs to reattempt the authentication.")
            }
        }

        builder.withDefaultAuthToken(
            OAuth2Credential("twitch", credentials.access)
        )

        return builder.build()
    }

    override val subscriptions: SubscriptionService = SubscriptionServiceImpl()

    override val auth: AuthenticationService = AuthenticationServiceImpl(_identity)

    override val version: Version = Version.V1_0
}