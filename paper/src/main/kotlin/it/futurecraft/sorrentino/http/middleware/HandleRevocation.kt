package it.futurecraft.sorrentino.http.middleware

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import it.futurecraft.sorrentino.http.models.messages.RevocationHttpMessage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

fun handleRevocation(call: ApplicationCall, body: JsonElement) {
    val revocation = Json.decodeFromJsonElement<RevocationHttpMessage>(body)

    println("The subscription to \"${revocation.subscription.type}\" has been revoked!")
    println("Reason: ${revocation.subscription.status}")

    call.response.status(HttpStatusCode.NoContent)
}