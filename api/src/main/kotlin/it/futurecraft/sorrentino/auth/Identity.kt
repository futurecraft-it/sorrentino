package it.futurecraft.sorrentino.auth

import kotlinx.serialization.Serializable

/**
 * Represents the identity of a Twitch application.
 *
 * @param id The app client id.
 * @param secret The app client secret.
 */
@Serializable
data class Identity(
    val id: String,
    val secret: String
) {
    companion object {
        fun default() = Identity(
            id = "your-client-id",
            secret = "your-client-secret"
        )
    }
}
