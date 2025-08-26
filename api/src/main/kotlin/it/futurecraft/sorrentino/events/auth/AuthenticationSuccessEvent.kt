package it.futurecraft.sorrentino.events.auth

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Called when a player successfully authenticates.
 *
 * @param player The player that successfully authenticated.
 */
class AuthenticationSuccessEvent(
    val player: Player,
) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}