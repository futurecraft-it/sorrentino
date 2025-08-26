package it.futurecraft.sorrentino

import com.github.twitch4j.TwitchClient
import it.futurecraft.sorrentino.auth.Identity
import it.futurecraft.sorrentino.services.AuthenticationService
import it.futurecraft.sorrentino.services.AuthenticationServiceImpl
import it.futurecraft.sorrentino.services.SubscriptionService
import it.futurecraft.sorrentino.services.SubscriptionServiceImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SorrentinoImpl : Sorrentino, KoinComponent {
    override val client: TwitchClient by inject()

    override val subscriptions: SubscriptionService = SubscriptionServiceImpl()

    override val auth: AuthenticationService = AuthenticationServiceImpl(
        Identity("nk8kl64j7bik05uiu3b9jtrnwqhgte", "")
    )

    override val version: Version = Version.V1_0
}