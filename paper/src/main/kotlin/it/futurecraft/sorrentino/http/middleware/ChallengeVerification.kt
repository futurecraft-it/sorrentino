package it.futurecraft.sorrentino.http.middleware

import com.github.twitch4j.common.util.TypeConvert
import com.github.twitch4j.eventsub.EventSubNotification
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun challengeVerification(call: ApplicationCall) {
    val body = call.receiveText()
    val message: EventSubNotification = TypeConvert.jsonToObject(body, EventSubNotification::class.java)

    call.response.status(HttpStatusCode.OK)
    call.respondText(message.challenge!!)
}