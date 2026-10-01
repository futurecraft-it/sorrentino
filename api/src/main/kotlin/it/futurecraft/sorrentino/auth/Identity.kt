package it.futurecraft.sorrentino.auth

import kotlinx.serialization.Serializable

/**
 * Twitch App identity.
 *
 * @property id The app id.
 * @property secret The app secret.
 */
@Serializable
data class Identity(
    val id: String,
    val secret: String
)
