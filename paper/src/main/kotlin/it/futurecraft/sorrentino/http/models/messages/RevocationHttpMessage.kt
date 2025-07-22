package it.futurecraft.sorrentino.http.models.messages

import it.futurecraft.sorrentino.http.models.HttpMessage
import it.futurecraft.sorrentino.twitch.Subscription

data class RevocationHttpMessage(
    override val subscription: Subscription
) : HttpMessage