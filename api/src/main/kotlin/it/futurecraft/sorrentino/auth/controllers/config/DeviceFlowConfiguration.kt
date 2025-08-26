package it.futurecraft.sorrentino.auth.controllers.config

import it.futurecraft.sorrentino.auth.Identity
import it.futurecraft.sorrentino.auth.controllers.FlowConfiguration

data class DeviceFlowConfiguration(
    var identity: Identity
) : FlowConfiguration {
    companion object {
        fun default() = DeviceFlowConfiguration(Identity.default())
    }
}
