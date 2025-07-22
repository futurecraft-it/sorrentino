package it.futurecraft.sorrentino.http.models.messages

import it.futurecraft.sorrentino.http.models.HttpMessage
import it.futurecraft.sorrentino.twitch.Subscription
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeHttpMessage(val challenge: String, override val subscription: Subscription) : HttpMessage