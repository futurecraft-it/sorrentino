package it.futurecraft.sorrentino.events.auth

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * This event is triggered when an OAuth2 Flow has been started.
 *
 * Cancelling this event will stop the authentication process.
 *
 * @param player The player who has to identify.
 * @param url The url the user has to identify to.
 * @param isDevice Whether the flow is a device code flow or not.
 * @param device The device code information, if applicable.
 */
class AuthenticationStartEvent(
    val player: Player,
    val url: String,
    val isDevice: Boolean = false,
    val device: String? = null,
) : Event(), Cancellable {
    private var _cancelled = false

    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST

    override fun isCancelled(): Boolean {
        return _cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        _cancelled = cancel
    }
}