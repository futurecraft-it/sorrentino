package it.futurecraft.sorrentino

import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class NotificationEvent(val twitchEvent: TwitchEvent) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        fun getHandlersList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}