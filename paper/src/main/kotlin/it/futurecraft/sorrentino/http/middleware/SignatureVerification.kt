package it.futurecraft.sorrentino.http.middleware

import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

fun hmac(message: String, secret: String): String {
    val mac = Mac.getInstance("HmacSHA256")
    val key = SecretKeySpec(secret.toByteArray(), "HmacSHA256")

    mac.init(key)
    val result = mac.doFinal(message.toByteArray())

    return result.joinToString("") { "%02x".format(it and 0xff.toByte()) }
}


fun verifySignature(call: RoutingCall, body: JsonElement, secret: String): Boolean {
    val signature = call.request.headers["Twitch-Eventsub-Message-Signature"]

    val message = call.request.headers["Twitch-Eventsub-Message-Id"] + call.request.headers["Twitch-Eventsub-Message-Timestamp"] + Json.encodeToString(body)
    val generated = "sha256=" + hmac(message, secret)

    return generated == signature
}