package it.futurecraft.sorrentino.http

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.request.*
import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import io.ktor.utils.io.core.*

import it.futurecraft.sorrentino.http.middleware.challengeVerification
import it.futurecraft.sorrentino.http.middleware.handleNotification
import it.futurecraft.sorrentino.http.models.HttpMessage
import it.futurecraft.sorrentino.http.plugins.VerifySignaturePlugin

import kotlinx.serialization.json.JsonElement

class HttpServer : Closeable {
    private val server = embeddedServer(Netty, port = 8080) {
        install(DoubleReceive)

        install(VerifySignaturePlugin) {
            secret = "1okly1rfdgdqiq50hz6kjm45yj3cao" // THIS IS A FAKE SECRET YOU BLATANT
        }

        install(ContentNegotiation) {
            json()
        }

        routing {
            post("/webhook/callback") {
                val body = call.receive<JsonElement>()

                return@post when (call.request.headers["Twitch-Eventsub-Message-Type"]) {
                    HttpMessage.TYPE.VERIFICATION.type -> challengeVerification(call, body)
                    HttpMessage.TYPE.NOTIFICATION.type -> handleNotification(call, body)
                    HttpMessage.TYPE.REVOCATION.type -> handleNotification(call, body)

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