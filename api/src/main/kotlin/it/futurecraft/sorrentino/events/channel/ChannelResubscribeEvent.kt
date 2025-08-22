package it.futurecraft.sorrentino.events.channel

import com.github.twitch4j.common.enums.SubscriptionPlan
import com.github.twitch4j.eventsub.domain.Message
import com.github.twitch4j.helix.domain.User
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is triggered when a user resubscribes to a channel.
 *
 * @param user The user who resubscribed.
 * @param broadcaster The broadcaster to whom the user resubscribed.
 * @param tier The subscription tier.
 * @param months The total number of months the user has been subscribed.
 * @param duration The number of months the user has subscribed for.
 * @param streak The current subscription streak, or null if the user has no streak.
 * @param message The message associated with the resubscription.
 *
 * @see [User]
 * @see [Message]
 * @see [SubscriptionPlan]
 */
class ChannelResubscribeEvent(
    val user: User,
    val broadcaster: User,
    val tier: SubscriptionPlan,
    val months: Int,
    val duration: Int,
    val streak: Int?,
    val message: Message
) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}