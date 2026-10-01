package it.futurecraft.sorrentino.database.credential

import it.futurecraft.sorrentino.database.user.User
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.until
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import kotlin.time.Clock

class Credential(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Credential>(CredentialTable)

    var access by CredentialTable.access

    var refresh by CredentialTable.refresh

    var expiresAt by CredentialTable.expiresAt

    var user by User referencedOn CredentialTable.userId

    val expiresIn: Int
        get() = Clock.System.now()
            .until(expiresAt.toInstant(TimeZone.UTC), DateTimeUnit.SECOND)
            .toInt()
}