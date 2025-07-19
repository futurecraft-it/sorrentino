package it.futurecraft.sorrentino.twitch.messages

import it.futurecraft.sorrentino.twitch.Subscription
import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import kotlinx.serialization.Serializable

@Serializable
data class EventHttpMessage(
    val event: TwitchEvent,
    override val subscription: Subscription
) : HttpMessage
