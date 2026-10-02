package it.futurecraft.sorrentino.service

import com.github.twitch4j.auth.domain.TwitchScopes
import it.futurecraft.sorrentino.auth.flow.Flow
import net.kyori.adventure.audience.Audience

/**
 * Service for handling authentication with Twitch.
 */
interface AuthenticationService {
    companion object {
        @JvmStatic
        val ENDPOINT = "https://id.twitch.tv/oauth2"
    }

    /**
     * Authenticates a user with the specified scopes and flow.
     *
     * @param target The user to authenticate.
     * @param scopes The scope you want the user to allow.
     * @param flow The type of Authentication Flow you want to use.
     */
    suspend fun authenticate(target: Audience, scopes: List<TwitchScopes>, flow: Flow = Flow.DEVICECODE)

    /**
     * Verifies the authentication status of a user.
     * @param target The user to verify its credentials.
     * @return true if the user is authenticated, false otherwise.
     */
    suspend fun verify(target: Audience): Boolean

    /**
     * Refreshes the authentication tokens for a user.
     *
     * @param target The user to refresh its credentials.
     * @return true if the refresh was necessary, false otherwise.
     */
    suspend fun refresh(target: Audience): Boolean
}