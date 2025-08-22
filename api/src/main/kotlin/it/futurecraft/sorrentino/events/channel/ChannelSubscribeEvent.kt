package it.futurecraft.sorrentino.events.channel

import com.github.twitch4j.common.enums.SubscriptionPlan
import com.github.twitch4j.helix.domain.User
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is triggered when a new user subscribes to a channel.
 *
 * This event do not include resubscriptions.
 *
 * @param user The user who subscribed.
 * @param broadcaster The broadcaster of the channel where the subscription occurred.
 * @param tier The subscription tier.
 * @param gift Whether the subscription was gifted.
 *
 * @see [User]
 * @see [SubscriptionPlan]
 */
class ChannelSubscribeEvent(
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