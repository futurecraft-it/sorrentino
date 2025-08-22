package it.futurecraft.sorrentino.events.channel

import com.github.twitch4j.helix.domain.User
import kotlinx.datetime.LocalDateTime
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is called when a user follows a channel.
 *
 * @param user The user who followed the channel.
 * @param broadcaster The broadcaster whose channel was followed.
 * @param followedAt The date and time when the follow occurred.
 * @see [User]
 */
class ChannelFollowEvent(
    val user: User,
    val broadcaster: User,
    val followedAt: LocalDateTime
) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}