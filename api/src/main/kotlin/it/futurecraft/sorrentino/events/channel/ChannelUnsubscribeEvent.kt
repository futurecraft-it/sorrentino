package it.futurecraft.sorrentino.events.channel

import com.github.twitch4j.common.enums.SubscriptionPlan
import com.github.twitch4j.helix.domain.User
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is triggered when a user unsubscribes from a channel.
 *
 * @param user The user who unsubscribed.
 * @param broadcaster The broadcaster from whom the user unsubscribed.
 * @param tier The subscription plan tier.
 * @param gift Whether the subscription was a gift.
 *
 * @see [User]
 * @see [SubscriptionPlan]
 */
class ChannelUnsubscribeEvent (
    val user: User,
    val broadcaster: User,
    val tier: SubscriptionPlan,
    val gift: Boolean
) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}