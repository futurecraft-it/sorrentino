package it.futurecraft.sorrentino.auth.flow

import com.github.twitch4j.auth.domain.TwitchScopes
import net.kyori.adventure.audience.Audience

/**
 * The controller for OAuth2 authentication flows.
 */
interface FlowController {
    /**
     * Starts the authentication process.
     *
     * @param target The audience to authenticate.
     * @param scopes The list of scopes to request.
     */
    suspend fun start(target: Audience, scopes: List<TwitchScopes>)
}