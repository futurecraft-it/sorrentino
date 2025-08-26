package it.futurecraft.sorrentino.http

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.core.*
import it.futurecraft.sorrentino.http.middleware.challengeVerification
import it.futurecraft.sorrentino.http.middleware.handleNotification
import it.futurecraft.sorrentino.http.middleware.handleRevocation
import it.futurecraft.sorrentino.http.plugins.VerifySignaturePlugin
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class HttpServer : Closeable, KoinComponent {
    private val server = embeddedServer(Netty, port = 8080) {
        install(DoubleReceive)

        install(ContentNegotiation) {
            json(Json { prettyPrint = true; isLenient = true })
        }

        install(VerifySignaturePlugin) {
            secret = "" // THIS IS A FAKE SECRET YOU BLATANT
            ignore = listOf("/favicon.ico")
        }

        routing {
            post("/webhook/callback") {
                when (call.request.headers["Twitch-Eventsub-Message-Type"]) {
                    "revocation" -> handleRevocation(call)
                    "notification" -> handleNotification(call)
                    "webhook_callback_verification" -> challengeVerification(call)

                    else -> call.respondText("Invalid message type.", status = HttpStatusCode.BadRequest)
                }
            }
        }
    }

    fun start() = server.start(wait = false)

    override fun close() = server.stop(0, 0)
}