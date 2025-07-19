package it.futurecraft.sorrentino.twitch

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Boundaries(
    @SerialName("start_pos")
    val startPos: Int,

    @SerialName("end_pos")
    val endPos: Int,
)

@Serializable
data class Automod(
    val category: String,
    val level: Int,
    val boundaries: Boundaries
) {
    @Serializable
    enum class Reason {
        @SerialName("automod")
        AUTOMOD,

        @SerialName("blocked_term")
        BLOCKED_TERM,
    }

    @Serializable
    enum class Status {
        @SerialName("denied")
        DENIED,

        @SerialName("approved")
        APPROVED,

        @SerialName("expired")
        EXPIRED
    }

    @Serializable
    enum class Action {
        @SerialName("add_permitted")
        ADD_PERMITTED,

        @SerialName("remove_permitted")
        REMOVE_PERMITTED,

        @SerialName("add_blocked")
        ADD_BLOCKED,

        @SerialName("remove_blocked")
        REMOVE_BLOCKED,
    }
}

@Serializable
data class BlockedTerm(
    @SerialName("terms_found")
    val termsFound: List<TermFound>,
) {
    @Serializable
    data class TermFound(
        @SerialName("term_id")
        val termId: String,

        val boundary: Boundaries,

        @SerialName("owner_broadcaster_user_id")
        val ownerBroadcasterUserId: String,

        @SerialName("owner_broadcaster_user_login")
        val ownerBroadcasterUserLogin: String,

        @SerialName("owner_broadcaster_user_name")
        val ownerBroadcasterUserName: String,
    )
}

