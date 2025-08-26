package it.futurecraft.sorrentino.database.user

import org.jetbrains.exposed.v1.core.dao.id.UUIDTable

object UserTable : UUIDTable("user", "uuid") {
    val twitchId = integer("twitch_id").uniqueIndex()

    val twitchName = varchar("twitch_name", 25)

    val displayName = varchar("display_name", 16)

    val streamer = bool("is_streamer").default(false)

}