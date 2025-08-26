package it.futurecraft.sorrentino.auth.controllers.config

import it.futurecraft.sorrentino.auth.Identity
import it.futurecraft.sorrentino.auth.controllers.FlowConfiguration

data class AuthFlowConfiguration(
    var identity: Identity,
    var redirect: String,
) : FlowConfiguration {
    companion object {
        fun default() = AuthFlowConfiguration(
            identity = Identity.default(),
            redirect = "http://localhost:8080/authorize"
        )
    }
}
