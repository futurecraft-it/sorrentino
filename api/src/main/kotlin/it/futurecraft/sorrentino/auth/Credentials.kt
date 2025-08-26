package it.futurecraft.sorrentino.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represent the credentials received after a successful authentication.
 *
 * @param access The access token.
 * @param refresh The refresh token.
 * @param expiration The time in seconds at which the access token expires.
 */
@Serializable
data class Credentials(
    @SerialName("access_token")
    val access: String,

    @SerialName("refresh_token")
    val refresh: String,

    @SerialName("expires_in")
    val expiration: Int
)
