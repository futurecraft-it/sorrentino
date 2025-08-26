package it.futurecraft.sorrentino.auth.controllers

import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.auth.domain.TwitchScopes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import it.futurecraft.sorrentino.SorrentinoPlugin
import it.futurecraft.sorrentino.auth.Credentials
import it.futurecraft.sorrentino.auth.Device
import it.futurecraft.sorrentino.auth.Identity
import it.futurecraft.sorrentino.database.credentials.CredentialsEntity
import it.futurecraft.sorrentino.database.credentials.CredentialsTable
import it.futurecraft.sorrentino.database.user.UserEntity
import it.futurecraft.sorrentino.events.auth.AuthenticationCancelledEvent
import it.futurecraft.sorrentino.events.auth.AuthenticationStartEvent
import it.futurecraft.sorrentino.events.auth.AuthenticationSuccessEvent
import it.futurecraft.sorrentino.services.AuthenticationService
import it.futurecraft.sorrentino.services.BASE_URL
import kotlinx.coroutines.delay
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder.eq
import org.jetbrains.exposed.v1.core.and
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.collections.emptyList
import kotlin.time.Clock
import kotlin.time.Instant

internal class DeviceFlowController(val identity: Identity) : FlowController, KoinComponent {
    private val _plugin by inject<SorrentinoPlugin>()

    private suspend fun initialize(scopes: List<TwitchScopes>): Device {
        val client = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json { prettyPrint = true; isLenient = true })
            }
        }

        val res = client.submitForm(
            url = "${AuthenticationService.BASE_URL}/device", formParameters = parameters {
                append("client_id", identity.id)
                append("scopes", scopes.joinToString(" "))
            })

        val data = res.body<Device>()

        client.close()

        return data
    }

    override suspend fun start(key: Key, player: Player, scopes: List<TwitchScopes>) {
        val device = initialize(scopes)

        val event = AuthenticationStartEvent(player, device.verificationUri, true, device.user)

        // We're on a coroutine so we should go back to the main thread to call the event
        Bukkit.getScheduler().runTask(_plugin, Runnable {
            if (!event.callEvent()) {
                val cancelled = AuthenticationCancelledEvent(player, null)
                cancelled.callEvent()
                return@Runnable
            }

            val expiresAt = Clock.System.now().plus(device.expiration, DateTimeUnit.SECOND)

            // ASYNC!!!!
            _plugin.launch { authenticate(key, player, scopes, device.code, device.interval, expiresAt) }
        })
    }

    override suspend fun authenticate(
        key: Key,
        player: Player,
        scopes: List<TwitchScopes>,
        code: String,
        interval: Int,
        expiration: Instant
    ) {
        delay(interval * 1000L)

        val client = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json { prettyPrint = true; isLenient = true; ignoreUnknownKeys = true })
            }
        }

        val res = client.submitForm(
            url = "${AuthenticationService.BASE_URL}/token", formParameters = parameters {
                append("client_id", identity.id)
                append("scopes", scopes.joinToString(" "))
                append("device_code", code)
                append("grant_type", "urn:ietf:params:oauth:grant-type:device_code")
            })

        if (res.status == HttpStatusCode.OK) {
            val data = res.body<Credentials>()
            val expiresAt = Clock.System.now().plus(data.expiration, DateTimeUnit.SECOND)

            val user = UserEntity.findById(player.uniqueId) ?: register(player, data)

            val credentials = CredentialsEntity.findSingleByAndUpdate(
                op = (CredentialsTable.key eq key) and (CredentialsTable.userId eq user.id)
            ) {
                it.access = data.access
                it.refresh = data.refresh
                it.expiresAt = expiresAt.toLocalDateTime(TimeZone.currentSystemDefault())
            }

            if (credentials == null) {
                CredentialsEntity.new {
                    this.key = key
                    this.user = user
                    this.access = data.access
                    this.refresh = data.refresh
                    this.expiresAt = expiresAt.toLocalDateTime(TimeZone.currentSystemDefault())
                }
            }

            val event = AuthenticationSuccessEvent(player)
            Bukkit.getScheduler().runTask(_plugin, Runnable {
                event.callEvent()
            })

            return client.close()
        }

        if (Clock.System.now() > expiration) {
            // TODO("Change message to config message")
            val event = AuthenticationCancelledEvent(
                player,
                Component.text("Authentication timed out.")
            )

            Bukkit.getScheduler().runTask(_plugin, Runnable {
                event.callEvent()
            })

            return client.close()
        }

        client.close()

        return authenticate(key, player, scopes, code, interval, expiration)
    }

    fun register(player: Player, credentials: Credentials): UserEntity {
        val client = TwitchClientBuilder.builder()
            .withClientId(identity.id)
            .withClientSecret(identity.secret)
            .withEnableHelix(true)
            .build()

        val list = client.helix.getUsers(credentials.access, emptyList<String>(), emptyList<String>())
            .execute()

        val user = list.users.first()

        return UserEntity.new(player.uniqueId) {
            twitchId = user.id.toInt()
            twitchName = user.login
            displayName = player.displayName().toString()
        }
    }
}