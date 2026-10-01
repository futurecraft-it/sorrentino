package it.futurecraft.sorrentino.database.credential

import it.futurecraft.sorrentino.database.user.UserTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.datetime

object CredentialTable : IntIdTable("credential") {
    val access = varchar("access_token", 64)

    val refresh = varchar("refresh_token", 64)

    val expiresAt = datetime("expires_at")

    val userId = reference("user_id", UserTable, onDelete = ReferenceOption.CASCADE)
        .uniqueIndex()
}