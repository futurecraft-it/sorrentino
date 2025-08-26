package it.futurecraft.sorrentino.database.user

import it.futurecraft.sorrentino.database.User
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.UUIDEntity
import org.jetbrains.exposed.v1.dao.UUIDEntityClass
import java.util.*

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id), User {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    override var uuid: UUID = id.value

    override var twitchId by UserTable.twitchId

    override var twitchName by UserTable.twitchName

    override var displayName by UserTable.displayName

    override var streamer by UserTable.streamer
}
