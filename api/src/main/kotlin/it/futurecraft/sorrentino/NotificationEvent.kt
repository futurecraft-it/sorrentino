package it.futurecraft.sorrentino

import it.futurecraft.sorrentino.twitch.Subscription
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

abstract class NotificationEvent(
    val type: Subscription.Type
) : Event() {
    companion object {
        private val HANDLERS_LIST = HandlerList()

        fun getHandlerList() = HANDLERS_LIST
    }

    override fun getHandlers(): HandlerList = HANDLERS_LIST
}