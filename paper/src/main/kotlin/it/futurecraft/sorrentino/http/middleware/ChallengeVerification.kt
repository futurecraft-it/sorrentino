package it.futurecraft.sorrentino.http.middleware

import com.github.twitch4j.common.util.TypeConvert
import com.github.twitch4j.eventsub.EventSubNotification
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText
import it.futurecraft.sorrentino.http.models.messages.ChallengeResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

suspend fun challengeVerification(call: ApplicationCall) {
    val message: EventSubNotification = TypeConvert.jsonToObject(call.receiveText(), EventSubNotification::class.java)

    call.response.status(HttpStatusCode.OK)
    call.respondText(message.challenge!!)
}