package it.futurecraft.sorrentino.http.models

import it.futurecraft.sorrentino.twitch.Subscription

interface HttpMessage {
    val subscription: Subscription

    enum class TYPE(val type: String) {
        NOTIFICATION("notification"),
        VERIFICATION("webhook_callback_verification"),
        REVOCATION("revocation"),
    }
}