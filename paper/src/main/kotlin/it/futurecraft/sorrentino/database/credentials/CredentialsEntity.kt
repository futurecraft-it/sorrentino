package it.futurecraft.sorrentino.database.credentials

import it.futurecraft.sorrentino.database.Credentials
import it.futurecraft.sorrentino.database.user.UserEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class CredentialsEntity(id: EntityID<Int>) : IntEntity(id), Credentials<UserEntity> {
    companion object : IntEntityClass<CredentialsEntity>(CredentialsTable)

    override var refresh by CredentialsTable.refreshToken

    override var key by CredentialsTable.key

    override var user by UserEntity referencedOn CredentialsTable.userId

    override var access by CredentialsTable.accessToken

    override var expiresAt by CredentialsTable.expiresAt
}