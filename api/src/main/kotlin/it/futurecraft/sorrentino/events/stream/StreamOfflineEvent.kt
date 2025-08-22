package it.futurecraft.sorrentino.events.stream

import com.github.twitch4j.helix.domain.User
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is fired when a streamer goes offline.
 *
 * @param broadcaster The user who went offline.
 * @see User
 */
class StreamOfflineEvent(
    val broadcaster: User
) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}