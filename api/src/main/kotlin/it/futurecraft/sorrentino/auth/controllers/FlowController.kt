package it.futurecraft.sorrentino.auth.controllers

import com.github.twitch4j.auth.domain.TwitchScopes
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player
import kotlin.time.Instant

/**
 * Represent an authentication flow for OAuth.
 */
interface FlowController  {
    /**
     * Starts the authentication flow for the required scopes.
     *
     * @param key The unique identifier used to store the credentials.
     * @param player The player to ask the authentication to.
     * @param scopes The scope to ask the user to authenticate for.
     */
    suspend fun start(key: Key, player: Player, scopes: List<TwitchScopes>)

    /**
     * Authenticate the user with the given code.
     *
     * @param key The unique identifier used to store the credentials.
     * @param player The player to authenticate.
     * @param scopes The scopes to authenticate for.
     * @param code The code to authenticate with.
     * @param interval The interval (in seconds) to wait between each request. Irrelevant if you're using Authentication Code Flow.
     * @param expiration The time at which the authentication request expires. Irrelevant if you're using Authentication Code Flow.
     */
    suspend fun authenticate(key: Key, player: Player, scopes: List<TwitchScopes>, code: String, interval: Int, expiration: Instant)
}