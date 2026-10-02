package it.futurecraft.sorrentino.event.auth

import it.futurecraft.sorrentino.event.Event
import net.kyori.adventure.audience.Audience

/**
 * Called when a player successfully authenticates.
 *
 * @param target The user that successfully authenticated.
 * @param username The target's twitch username (aka login).
 */
data class AuthenticationSuccessEvent(val target: Audience, val username: String) : Event