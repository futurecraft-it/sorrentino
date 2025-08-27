package it.futurecraft.sorrentino

import com.github.twitch4j.TwitchClient
import it.futurecraft.sorrentino.auth.Identity
import it.futurecraft.sorrentino.services.AuthenticationService
import it.futurecraft.sorrentino.services.SubscriptionService
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player

/**
 * The main interface for interacting with the Sorrentino API.
 */
interface Sorrentino {
    /**
     * Creates a Twitch client for the main identity without specific credentials.
     *
     * @return A Twitch client authenticated with the main identity.
     */
    fun client(): TwitchClient

    /**
     * Creates a Twitch client for the given identity without specific player credentials.
     *
     * @param identity The identity to use for the client.
     * @return A Twitch client authenticated with the given identity.
     */
    fun client(identity: Identity): TwitchClient


    /**
     * Creates a Twitch client for the given player using Sorrentino's main identity and credentials.
     *
     * @param player The player associated with the client.
     *
     * @throws IllegalArgumentException If the player has never authenticated.
     */
    suspend fun client(player: Player): TwitchClient

    /**
     * Creates a Twitch client for the given player and key using Sorrentino's main identity.
     *
     * @param player The player associated with the client.
     * @param key The key of the player's credentials. Null if you want to use Sorrentino's.
     *
     * @throws IllegalArgumentException If the player has never authenticated or the key has no associated credentials.
     */
    suspend fun client(player: Player, key: Key): TwitchClient

    /**
     * Creates a Twitch client for the given identity and player.
     *
     * @param identity The identity to use for the client.
     * @param player The player associated with the client.
     * @param key The key of the player's credentials.
     *
     * @throws IllegalArgumentException If the player has never authenticated or the key has no associated credentials.
     */
    suspend fun client(identity: Identity, player: Player, key: Key): TwitchClient


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