package it.futurecraft.sorrentino.database.user

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.*

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(UserTable)

    var username by UserTable.username

    var twitchName by UserTable.twitchName

    var twitchId by UserTable.twitchId

    var streamer by UserTable.streamer
}