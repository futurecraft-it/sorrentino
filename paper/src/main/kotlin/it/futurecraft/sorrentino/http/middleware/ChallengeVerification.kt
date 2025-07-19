package it.futurecraft.sorrentino.http.middleware

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import it.futurecraft.sorrentino.twitch.messages.ChallengeHttpMessage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

suspend fun challengeVerification(call: ApplicationCall, body: JsonElement) {
    val message = Json.decodeFromJsonElement<ChallengeHttpMessage>(body)
    call.response.status(HttpStatusCode.OK)
    call.respondText(message.challenge)
}