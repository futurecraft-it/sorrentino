package it.futurecraft.sorrentino.auth.flow


import com.github.twitch4j.auth.domain.TwitchScopes
import com.github.twitch4j.helix.TwitchHelixBuilder
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import it.futurecraft.sorrentino.auth.Device
import it.futurecraft.sorrentino.auth.Identity
import it.futurecraft.sorrentino.database.credential.CredentialTable
import it.futurecraft.sorrentino.database.user.User
import it.futurecraft.sorrentino.event.EventBus
import it.futurecraft.sorrentino.event.auth.AuthenticationCancelledEvent
import it.futurecraft.sorrentino.event.auth.AuthenticationStartEvent
import it.futurecraft.sorrentino.event.auth.AuthenticationSuccessEvent
import it.futurecraft.sorrentino.service.AuthenticationService
import it.futurecraft.sorrentino.utils.wrapper.SchedulerWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import net.kyori.adventure.audience.Audience
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.upsert
import java.util.*
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

private typealias AudienceIdentity = net.kyori.adventure.identity.Identity

class DeviceFlowController(
    private val _identity: Identity,
    private val _scheduler: SchedulerWrapper,
    private val _eventbus: EventBus
) : FlowController {
    private val _client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { isLenient = true; ignoreUnknownKeys = true })
        }
    }

    private suspend fun init(scopes: List<TwitchScopes>): Device {
        val res = _client.submitForm("${AuthenticationService.ENDPOINT}/device", formParameters = parameters {
            append("client_id", _identity.id)
            append("scopes", scopes.joinToString(" "))
        })

        return res.body<Device>()
    }

    override suspend fun start(target: Audience, scopes: List<TwitchScopes>) = withContext(Dispatchers.IO) {
        val device = init(scopes)
        val event = AuthenticationStartEvent(device.verificationUri, device.code, target)

        _scheduler.sync {
            val expiration = Clock.System.now() + 120.seconds

            if (_eventbus.publish(event)) {
                _scheduler.async { poll(target, scopes, device, expiration) }
            } else {
                val cancel = AuthenticationCancelledEvent(target, AuthenticationCancelledEvent.Reason.CANCELLED)
                _eventbus.publish(cancel)
            }
        }
    }

    private suspend fun poll(target: Audience, scopes: List<TwitchScopes>, device: Device, expiration: Instant) {
        if (Clock.System.now() >= expiration) {
            val event = AuthenticationCancelledEvent(target, AuthenticationCancelledEvent.Reason.TIMED_OUT)
            return _scheduler.sync { _eventbus.publish(event) }
        }

        val res = _client.submitForm("${AuthenticationService.ENDPOINT}/token", formParameters = parameters {
            append("client_id", _identity.id)
            append("device_code", device.code)
            append("scopes", scopes.joinToString(" "))
            append("grant_type", "urn:ietf:params:oauth:grant-type:device_code")
        })

        if (res.status == HttpStatusCode.OK) {
            val data = res.body<Data>()

            val uuid = target.getOrDefault(AudienceIdentity.UUID, null) ?: return _scheduler.sync {
                val event = AuthenticationCancelledEvent(target, AuthenticationCancelledEvent.Reason.INVALID_USER)
                _eventbus.publish(event)
            }

            val user = transaction { User.findById(uuid) } ?: register(uuid, target, data)

            val expiry = Clock.System.now()
                .plus(data.expiration.seconds)
                .toLocalDateTime(TimeZone.UTC)

            transaction {
                CredentialTable.upsert(CredentialTable.userId) {
                    it[userId] = user.id
                    it[access] = data.access
                    it[refresh] = data.refresh
                    it[expiresAt] = expiry
                }
            }

            val event = AuthenticationSuccessEvent(target, user.twitchName)
            return _scheduler.sync { _eventbus.publish(event) }
        }

        delay(device.interval.seconds)
        poll(target, scopes, device, expiration)
    }

    private fun register(uuid: UUID, target: Audience, data: Data): User {
        val name = target.getOrDefault(AudienceIdentity.NAME, "")!!

        val client = TwitchHelixBuilder.builder()
            .withClientId(_identity.id)
            .withClientSecret(_identity.secret)
            .build()

        val users = client.getUsers(data.access, null, null)
            .execute()

        val user = users.users.first()

        return transaction {
            User.new(uuid) {
                username = name
                twitchId = user.id
                twitchName = user.displayName
            }
        }
    }

    @Serializable
    data class Data(
        @SerialName("access_token")
        val access: String,

        @SerialName("refresh_token")
        val refresh: String,

        @SerialName("expires_in")
        val expiration: Int
    )
}