package it.futurecraft.sorrentino.services

import com.github.twitch4j.auth.domain.TwitchScopes
import it.futurecraft.sorrentino.auth.controllers.FlowController
import it.futurecraft.sorrentino.auth.controllers.FlowType
import it.futurecraft.sorrentino.auth.controllers.FlowConfiguration
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player

interface AuthenticationService {
    companion object;

    /**
     * Creates a new flow controller of the specified type, applying the given configuration block.
     *
     * This can be useful if you want to use Sorrentino's credential storage for a different client than the one used from Sorrentino.
     *
     * At the moment only [FlowType.DEVICE_CODE] is supported. Other types will throw an [IllegalArgumentException].
     *
     * @param type The type of flow controller to create.
     * @param block A configuration block to customize the flow controller.
     * @return A new instance of the specified flow controller.
     *
     * @see FlowType
     * @see FlowController
     */
    fun <C : FlowConfiguration, T : FlowType<C>> controller(type: FlowType<C>, block: C.() -> Unit): FlowController

    /**
     * Initiate and starts the authentication flow for the scopes. If the authentication is successful, the credentials will be stored with the provided [key].
     *
     * @param player The player who has to identify.
     * @param key The key that will be used to store the token.
     * @param scopes A list of [TwitchScopes] that define the permissions being requested.
     */
    suspend fun <C : FlowConfiguration> authenticate(player: Player, key: Key, scopes: List<TwitchScopes>)


    /**
     * Verifies if there are valid credentials stored for the provided [key].
     *
     * @param key The key associated with the stored credentials.
     * @return `true` if valid credentials exist for the given key, `false` otherwise.
     */
    suspend fun verify(key: Key): Boolean
}

/**
 * The base URL for Twitch authentication.
 */
val AuthenticationService.Companion.BASE_URL
    get() = "https://id.twitch.tv/oauth2"