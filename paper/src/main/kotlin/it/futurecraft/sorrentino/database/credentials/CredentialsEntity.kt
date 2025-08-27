package it.futurecraft.sorrentino.database.credentials

import it.futurecraft.sorrentino.database.Credentials
import it.futurecraft.sorrentino.database.user.UserEntity
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.until
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import kotlin.time.Clock

class CredentialsEntity(id: EntityID<Int>) : IntEntity(id), Credentials<UserEntity> {
    companion object : IntEntityClass<CredentialsEntity>(CredentialsTable)

    override var refresh by CredentialsTable.refreshToken

    override var key by CredentialsTable.key

    override var user by UserEntity referencedOn CredentialsTable.userId

    override var access by CredentialsTable.accessToken

    override var expiresAt by CredentialsTable.expiresAt

    override val expiresIn: Int
        get() = Clock.System.now().until(
            other = expiresAt.toInstant(TimeZone.currentSystemDefault()), unit = DateTimeUnit.SECOND
        ).toInt()
}