package it.futurecraft.sorrentino

import com.github.twitch4j.TwitchClient
import it.futurecraft.sorrentino.services.AuthenticationService
import it.futurecraft.sorrentino.services.SubscriptionService

/**
 * The main interface for interacting with the Sorrentino API.
 */
interface Sorrentino {
    /**
     * The Twitch4J client instance.
     */
    val client: TwitchClient

    /**
     * Service that helps to manage Twitch EventSub subscriptions.
     * @see [SubscriptionService]
     */
    val subscriptions: SubscriptionService

    /**
     * Service that helps to manage Twitch authentication.
     * @see [AuthenticationService]
     */
    val auth: AuthenticationService

    /**
     * The api version of Sorrentino.
     */
    val version: Version
}