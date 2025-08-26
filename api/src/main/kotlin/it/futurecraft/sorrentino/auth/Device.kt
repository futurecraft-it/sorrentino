package it.futurecraft.sorrentino.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing the response from Twitch's Device Authorization Flow.
 *
 * @property code The device code used for polling the token endpoint.
 * @property user The user code that the user needs to enter on the verification URI.
 * @property interval The minimum amount of time in seconds that the client MUST wait between polling requests to the token endpoint.
 * @property expiration The time in seconds before the device code and user code expire.
 * @property verificationUri The URI where the user needs to enter the user code to authorize the device.
 */
@Serializable
data class Device(
    @SerialName("device_code")
    val code: String,

    @SerialName("user_code")
    val user: String,

    val interval: Int,

    @SerialName("expires_in")
    val expiration: Int,

    @SerialName("verification_uri")
    val verificationUri: String
)
