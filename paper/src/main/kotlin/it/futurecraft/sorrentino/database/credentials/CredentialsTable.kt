package it.futurecraft.sorrentino.database.credentials

import it.futurecraft.sorrentino.database.types.key
import it.futurecraft.sorrentino.database.user.UserTable
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.datetime

object CredentialsTable : IntIdTable("credentials") {
    val key = key("key")

    val userId = reference("user_id", UserTable)

    val accessToken = varchar("access_token", 512)

    val refreshToken = varchar("refresh_token", 512)

    val expiresAt = datetime("expires_at")

    init { index("index", true, key, userId) }
}