package it.futurecraft.sorrentino.http

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.receive
import io.ktor.server.routing.*
import it.futurecraft.sorrentino.http.middleware.challengeVerification
import it.futurecraft.sorrentino.http.middleware.verifySignature
import it.futurecraft.sorrentino.twitch.messages.HttpMessageType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.util.concurrent.TimeUnit

class HttpServer(configuration: HttpServerConfiguration) {
    private val server = embeddedServer(Netty, configuration.port, host = configuration.host) {
        install(ContentNegotiation) {
            json(Json { prettyPrint = true; isLenient = true })
        }

        routing {
            post("/") {
                val body = call.receive<JsonElement>()
                if (!verifySignature(call, body, "1okly6kjm45yj3cao1rfdgdqiq50hz")) {
                    call.response.status(HttpStatusCode.Forbidden)
                    return@post
                }

                val type = call.request.headers["Twitch-Eventsub-Message-Type"]
                when (type) {
                    HttpMessageType.VERIFICATION.value -> challengeVerification(call, body)
                    HttpMessageType.NOTIFICATION.value -> {}
                    HttpMessageType.REVOCATION.value -> {}
                }
            }
        }
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    private lateinit var job: Job

    fun start() {
        job = scope.launch {
            server.start(wait = false)
        }
    }

    fun stop() {
        server.stop(0, 0, TimeUnit.MILLISECONDS)
        job.cancel()
    }
}