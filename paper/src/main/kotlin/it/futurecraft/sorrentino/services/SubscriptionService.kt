package it.futurecraft.sorrentino.services

import com.github.twitch4j.TwitchClient
import com.github.twitch4j.eventsub.EventSubTransport
import com.github.twitch4j.eventsub.EventSubTransportMethod
import com.github.twitch4j.eventsub.condition.EventSubCondition
import com.github.twitch4j.eventsub.events.EventSubEvent
import com.github.twitch4j.eventsub.subscriptions.SubscriptionType
import it.futurecraft.sorrentino.Sorrentino
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class SubscriptionServiceImpl : SubscriptionService, KoinComponent {
    private val _api by inject<Sorrentino>()

    private val _client: TwitchClient
        get() = _api.client()

    private val _transport = EventSubTransport.builder()
        .method(EventSubTransportMethod.WEBHOOK)
        .callback("http://localhost:8080/webhook/callback")
        .secret("xxxx")
        .build()


    override suspend fun <C : EventSubCondition, E : EventSubEvent, B, T : SubscriptionType<C, B, E>> subscribe(
        type: T,
        block: (B) -> C
    ) {
        val sub = type.prepareSubscription(block, _transport)

        _client.helix.createEventSubSubscription(null, sub)
            .execute()
    }

    override suspend fun unsubscribe(id: String) {
        _client.helix.deleteEventSubSubscription(null, id)
            .execute()
    }
}