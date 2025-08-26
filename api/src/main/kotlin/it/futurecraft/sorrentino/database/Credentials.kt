package it.futurecraft.sorrentino.database

import kotlinx.datetime.LocalDateTime
import net.kyori.adventure.key.Key

/**
 * Represents a credentials for a given [User].
 *
 * @property key The key for these credentials.
 * @property user The user authenticated with these credentials.
 * @property access The access token.
 * @property refresh The refresh token.
 * @property expiresAt The timestamp of when the access token will be invalid.
 */
interface Credentials<E : User> {
    var key: Key

    var user: E

    var access: String

    var refresh: String

    var expiresAt: LocalDateTime
}