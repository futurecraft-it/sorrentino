package it.futurecraft.sorrentino.events.channel

import com.github.twitch4j.common.enums.SubscriptionPlan
import com.github.twitch4j.helix.domain.User
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is called when a user gifts subscriptions to a channel.
 *
 * @param user The user who gifted the subscriptions.
 * @param broadcaster The broadcaster who received the gifted subscriptions.
 * @param tier The subscription tier of the gifted subscriptions.
 * @param amount The number of subscriptions gifted in this event.
 * @param total The total number of subscriptions gifted by the user to the broadcaster (if available).
 * @param anonymous Whether the gift was anonymous.
 *
 * @see [User]
 * @see [SubscriptionPlan]
 */
class ChannelSubscriptionGiftEvent(
    val user: User,
    val broadcaster: User,
    val tier: SubscriptionPlan,
    val amount: Int,
    val total: Int?,
    val anonymous: Boolean
) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}