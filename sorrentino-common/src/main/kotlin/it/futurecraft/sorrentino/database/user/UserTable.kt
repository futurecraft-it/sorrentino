package it.futurecraft.sorrentino.database.user

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object UserTable : UUIDTable("user", "uuid") {
    val username = varchar("username", 16)
        .uniqueIndex()

    val twitchId = varchar("twitch_id", 32)
        .uniqueIndex()

    val twitchName = varchar("twitch_name", 32)
        .uniqueIndex()

    val streamer = bool("is_streamer")
        .default(false)
}