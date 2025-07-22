package it.futurecraft.sorrentino.http.middleware

import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.serialization.json.JsonElement

suspend fun handleNotification(call: ApplicationCall, body: JsonElement) {

    call.response.status(HttpStatusCode.NoContent)
}
