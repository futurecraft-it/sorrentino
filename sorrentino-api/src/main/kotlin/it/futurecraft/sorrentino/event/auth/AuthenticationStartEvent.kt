package it.futurecraft.sorrentino.event.auth

import it.futurecraft.sorrentino.event.Event
import net.kyori.adventure.audience.Audience

/**
 * This event is triggered when an OAuth2 Flow has been started.
 *
 * Cancelling this event will stop the authentication process.
 *
 * @param target The user who has to identify.
 * @param url The url the user has to identify to.
 * @param isDevice Whether the flow is a device code flow or not. (Default to true)
 * @param device The device code information, if applicable.
 */
data class AuthenticationStartEvent(
    val url: String,
    val device: String,
    val target: Audience,
    val isDevice: Boolean = true,
    override var cancelled: Boolean = false
) : Event.Cancellable
