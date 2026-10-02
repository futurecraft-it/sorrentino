package it.futurecraft.sorrentino.event.auth

import it.futurecraft.sorrentino.event.Event
import net.kyori.adventure.audience.Audience

/**
 * This event is triggered when an OAuth2 Flow has been cancelled.
 *
 * @param target The target whose authentication got cancelled.
 * @param reason The reason why the authentication has been cancelled.
 */
data class AuthenticationCancelledEvent(val target: Audience, val reason: Reason) : Event {
    /**
     * The reason an authentication flow might be cancelled
     */
    enum class Reason {
        /**
         * If the provided user has no UUID
         */
        INVALID_USER,

        /**
         * If the user took too much time to authenticate.
         */
        TIMED_OUT,

        /**
         * If the authentication has been cancelled by a third-party plugin.
         */
        CANCELLED
    }
}