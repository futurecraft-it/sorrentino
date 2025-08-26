package it.futurecraft.sorrentino.services

import com.github.twitch4j.auth.domain.TwitchScopes
import it.futurecraft.sorrentino.auth.Identity
import it.futurecraft.sorrentino.auth.controllers.DeviceFlowController
import it.futurecraft.sorrentino.auth.controllers.FlowConfiguration
import it.futurecraft.sorrentino.auth.controllers.FlowController
import it.futurecraft.sorrentino.auth.controllers.FlowType
import it.futurecraft.sorrentino.auth.controllers.config.DeviceFlowConfiguration
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player

class AuthenticationServiceImpl(identity: Identity) : AuthenticationService {
    private val _controller = DeviceFlowController(identity)

    override fun <C : FlowConfiguration, T : FlowType<C>> controller(
        type: FlowType<C>, block: C.() -> Unit
    ): FlowController = when (type) {
        FlowType.DEVICE_CODE -> {
            val conf = DeviceFlowConfiguration.default().apply(block as DeviceFlowConfiguration.() -> Unit)
            DeviceFlowController(conf.identity)
        }

        else -> throw IllegalArgumentException("Unsupported flow type: $type")
    }


    override suspend fun <C : FlowConfiguration> authenticate(player: Player, key: Key, scopes: List<TwitchScopes>) {
        _controller.start(key, player, scopes)
    }

    override suspend fun verify(key: Key): Boolean {
        TODO("Not yet implemented")
    }
}