package it.futurecraft.sorrentino.database

import java.util.*

/**
 * A player that connected his Twitch account.
 *
 * @property uuid The player UUID.
 * @property displayName The player display name.
 * @property twitchId The player's Twitch ID.
 * @property twitchName The player's Twitch Name.
 * @property streamer Whether the player's a streamer or not.
 */
interface User {
    var uuid: UUID

    var displayName: String

    var twitchId: Int

    var twitchName: String

    var streamer: Boolean
}