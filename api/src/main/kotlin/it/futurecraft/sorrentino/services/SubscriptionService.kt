package it.futurecraft.sorrentino.services

import com.github.twitch4j.eventsub.condition.EventSubCondition
import com.github.twitch4j.eventsub.events.EventSubEvent
import com.github.twitch4j.eventsub.subscriptions.SubscriptionType

/**
 * Service for managing Twitch EventSub subscriptions.
 */
interface SubscriptionService {
    /**
     * Subscribes to a Twitch EventSub event.
     *
     * @param type The type of subscription.
     * @param block A lambda to configure the subscription condition.
     */
    suspend fun <C : EventSubCondition, E : EventSubEvent, B, T : SubscriptionType<C, B, E>> subscribe(
        type: T,
        block: (B) -> C
    )

    /**
     * Unsubscribes from a Twitch EventSub event.
     *
     * @param id The ID of the subscription to unsubscribe from.
     */
    suspend fun unsubscribe(id: String)
}