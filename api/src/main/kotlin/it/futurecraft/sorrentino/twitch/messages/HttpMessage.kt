package it.futurecraft.sorrentino.twitch.messages

import it.futurecraft.sorrentino.twitch.Subscription
import kotlinx.serialization.Serializable

@Serializable
sealed interface HttpMessage {
    val subscription: Subscription
}

enum class HttpMessageType(val value: String) {
    VERIFICATION("webhook_callback_verification"),
    NOTIFICATION("notification"),
    REVOCATION("revocation")
}
