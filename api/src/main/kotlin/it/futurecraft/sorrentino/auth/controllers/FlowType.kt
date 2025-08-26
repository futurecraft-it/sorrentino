package it.futurecraft.sorrentino.auth.controllers

import it.futurecraft.sorrentino.auth.controllers.config.AuthFlowConfiguration
import it.futurecraft.sorrentino.auth.controllers.config.DeviceFlowConfiguration

/**
 * The type of OAuth2 flow to use.
 *
 * @param C The configuration type for the flow controller.
 */
interface FlowType<C : FlowConfiguration> {
    companion object {
        /**
         * The Authorization Code Flow.
         */
        val AUTHORIZATION_CODE = object : FlowType<AuthFlowConfiguration> {}

        /**
         * The Device Code Flow.
         */
        val DEVICE_CODE = object : FlowType<DeviceFlowConfiguration> {}
    }
}