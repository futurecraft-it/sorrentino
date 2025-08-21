package it.futurecraft.sorrentino.http.middleware

import com.github.twitch4j.common.util.TypeConvert
import com.github.twitch4j.eventsub.EventSubNotification
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import it.futurecraft.sorrentino.http.models.messages.RevocationResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

suspend fun handleRevocation(call: ApplicationCall) {
    val revocation: EventSubNotification = TypeConvert.jsonToObject(call.receiveText(), EventSubNotification::class.java)

    println("The subscription to \"${revocation.subscription.type.name}\" has been revoked!")
    println("Reason: ${revocation.subscription.status}")

    call.response.status(HttpStatusCode.NoContent)
}