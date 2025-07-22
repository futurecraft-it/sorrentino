package it.futurecraft.sorrentino.http.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

internal fun hmac(message: String, secret: String): String {
    val mac = Mac.getInstance("HmacSHA256")
    val key = SecretKeySpec(secret.toByteArray(), "HmacSHA256")

    mac.init(key)
    val result = mac.doFinal(message.toByteArray())

    return result.joinToString("") { "%02x".format(it and 0xff.toByte()) }
}

data class VerifySignaturePluginConfig(var secret: String = "")

val VerifySignaturePlugin = createApplicationPlugin(
    name = "VerifyRequestPlugin",
    createConfiguration = ::VerifySignaturePluginConfig
) {
    val secret = pluginConfig.secret

    pluginConfig.apply {
        onCall { call ->
            val signature = call.request.headers["Twitch-Eventsub-Message-Signature"]
            val messageId = call.request.headers["Twitch-Eventsub-Message-Id"]
            val timestamp = call.request.headers["Twitch-Eventsub-Message-Timestamp"]

            if (signature == null || timestamp == null || messageId == null) {
                call.respondText("Malformed request headers.", status = HttpStatusCode.BadRequest)
                return@onCall
            }

            val body = call.receive<JsonElement>()
            val message = "$messageId$timestamp" + Json.encodeToString(body)

            val generated = "sha256=${hmac(message, secret)}"

            if (generated != signature) {
                call.respondText("Invalid signature.", status = HttpStatusCode.Forbidden)
                return@onCall
            }
        }
    }
}