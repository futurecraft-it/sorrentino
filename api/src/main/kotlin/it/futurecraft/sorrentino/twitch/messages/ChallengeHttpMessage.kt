package it.futurecraft.sorrentino.twitch.messages

import it.futurecraft.sorrentino.twitch.Subscription
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeHttpMessage(
    val challenge: String,
    override val subscription: Subscription
) : HttpMessage
