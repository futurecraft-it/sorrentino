package it.futurecraft.sorrentino.twitch.messages

import it.futurecraft.sorrentino.twitch.Subscription

data class RevocationHttpMessage(
    override val subscription: Subscription,
) : HttpMessage
