package it.futurecraft.sorrentino.services

import com.github.twitch4j.auth.domain.TwitchScopes
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player

interface AuthenticationService {
    companion object;

    /**
     * Initiate and starts the authentication flow for the scopes. If the authentication is successful, the credentials will be stored with the provided [key].
     *
     * @param player The player who has to identify.
     * @param key The key that will be used to store the token.
     * @param scopes A list of [TwitchScopes] that define the permissions being requested.
     */
    suspend fun authenticate(key: Key, player: Player, scopes: List<TwitchScopes>)


    /**
     * Verifies if there are valid credentials stored for the provided [key].
     *
     * @param key The key associated with the stored credentials.
     * @param player The player to whom the credentials belong.
     * @return `true` if valid credentials exist for the given key, `false` otherwise.
     */
    suspend fun verify(key: Key, player: Player): Boolean

    /**
     * Refreshes the stored credentials for the provided [key].
     * If the refresh is successful, the new credentials will replace the old ones.
     * If the refresh fails (e.g., due to an invalid or expired refresh token), the old credentials will be deleted.
     *
     * @param key The key associated with the stored credentials.
     * @param player The player to whom the credentials belong.
     *
     * @return `true` if the credentials were successfully refreshed, `false` if the refresh failed and the old credentials were deleted.
     */
    suspend fun refresh(key: Key, player: Player): Boolean
}

/**
 * The base URL for Twitch authentication.
 */
val AuthenticationService.Companion.BASE_URL
    get() = "https://id.twitch.tv/oauth2"