package it.futurecraft.sorrentino.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A device's authentication details.
 *
 * @property code The device code.
 * @property user The user code.
 * @property interval The polling interval in seconds.
 * @property expiration The expiration time in seconds.
 * @property verificationUri The verification URI.
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