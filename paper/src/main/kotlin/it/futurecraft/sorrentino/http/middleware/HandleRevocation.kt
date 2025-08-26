package it.futurecraft.sorrentino.http.middleware

import com.github.twitch4j.common.util.TypeConvert
import com.github.twitch4j.eventsub.EventSubNotification
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*

suspend fun handleRevocation(call: ApplicationCall) {
    val body = call.receiveText()
    val revocation: EventSubNotification = TypeConvert.jsonToObject(body, EventSubNotification::class.java)

    println("The subscription to \"${revocation.subscription.type.name}\" has been revoked!")
    println("Reason: ${revocation.subscription.status}")

    call.response.status(HttpStatusCode.NoContent)
}