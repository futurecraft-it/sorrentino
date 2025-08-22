package it.futurecraft.sorrentino.events.channel

import com.github.twitch4j.helix.domain.User
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is triggered when a user cheers bits in a channel.
 *
 * @param user The user who cheered the bits.
 * @param broadcaster The broadcaster of the channel where the bits were cheered.
 * @param message The message that accompanied the cheer.
 * @param bits The number of bits that were cheered.
 * @param anonymous Whether the cheer was anonymous.
 *
 * @see [User]
 */
class ChannelCheerEvent (
    val user: User,
    val broadcaster: User,
    val message: String,
    val bits: Int,
    val anonymous: Boolean,
) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}