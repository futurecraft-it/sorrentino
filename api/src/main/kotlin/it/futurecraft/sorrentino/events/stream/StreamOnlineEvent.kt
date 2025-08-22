package it.futurecraft.sorrentino.events.stream

import com.github.twitch4j.helix.domain.Stream
import com.github.twitch4j.helix.domain.User
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is fired when a streamer goes live.
 *
 * @param broadcaster The user who started the stream.
 * @param stream The stream that was started.
 * @see User
 * @see Stream
 */
class StreamOnlineEvent(
    val broadcaster: User,
    val stream: Stream
) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}