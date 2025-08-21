package it.futurecraft.sorrentino.http

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.core.*
import it.futurecraft.sorrentino.http.middleware.challengeVerification
import it.futurecraft.sorrentino.http.middleware.handleNotification
import it.futurecraft.sorrentino.http.middleware.handleRevocation
import it.futurecraft.sorrentino.http.plugins.VerifySignaturePlugin

class HttpServer : Closeable {
    private val server = embeddedServer(Netty, port = 8080) {
        install(DoubleReceive)

        install(VerifySignaturePlugin) {
            secret = "1okly1rfdgdqiq50hz6kjm45yj3cao" // THIS IS A FAKE SECRET YOU BLATANT
        }

        routing {
            post("/webhook/callback") {
                when (call.request.headers["Twitch-Eventsub-Message-Type"]) {
                    "revocation"-> handleRevocation(call)
                    "notification" -> handleNotification(call)
                    "webhook_callback_verification" -> challengeVerification(call)

                    else -> call.respondText("Invalid message type.", status = HttpStatusCode.BadRequest)
                }
            }
        }
    }

    fun start() =
        server.start(wait = false)

    override fun close() =
        server.stop(0, 0)
}