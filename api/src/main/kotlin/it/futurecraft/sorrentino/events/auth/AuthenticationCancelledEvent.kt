package it.futurecraft.sorrentino.events.auth

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is triggered when an OAuth2 Flow has been cancelled.
 *
 * @param player The player who cancelled the authentication.
 * @param reason The reason why the authentication was cancelled, if any.
 */
class AuthenticationCancelledEvent(
    val player: Player,
    val reason: Component?
) : Event() {
    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST
}