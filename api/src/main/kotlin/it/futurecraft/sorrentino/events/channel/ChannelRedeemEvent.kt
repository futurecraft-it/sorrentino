package it.futurecraft.sorrentino.events.channel

import com.github.twitch4j.eventsub.domain.RedemptionStatus
import com.github.twitch4j.eventsub.domain.Reward
import com.github.twitch4j.helix.domain.User
import kotlinx.datetime.LocalDateTime
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is called when a user redeems a channel point reward.
 *
 * @param id The unique identifier of the redemption.
 * @param user The user who redeemed the reward.
 * @param broadcaster The broadcaster whose channel points were used.
 * @param message The message associated with the redemption (if any).
 * @param reward The reward that was redeemed.
 * @param status The status of the redemption (e.g., FULFILLED, CANCELED).
 * @param redeemedAt The timestamp when the redemption occurred.
 *
 * @see [User]
 * @see [Reward]
 * @see [RedemptionStatus]
 */
class ChannelRedeemEvent(
    val id: String,
    val user: User,
    val broadcaster: User,
    val message: String,
    val reward: Reward,
    val status: RedemptionStatus,
    val redeemedAt: LocalDateTime
) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}