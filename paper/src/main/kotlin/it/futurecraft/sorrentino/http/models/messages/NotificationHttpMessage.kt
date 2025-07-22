package it.futurecraft.sorrentino.http.models.messages

import it.futurecraft.sorrentino.http.models.HttpMessage
import it.futurecraft.sorrentino.twitch.Subscription
import kotlinx.serialization.json.JsonElement

data class NotificationHttpMessage(
    override val subscription: Subscription,
    val event: JsonElement,
) : HttpMessage
