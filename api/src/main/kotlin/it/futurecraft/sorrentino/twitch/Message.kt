package it.futurecraft.sorrentino.twitch

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val text: String,
    val fragments: List<Fragment>,
) {
    @Serializable
    data class Fragment(
        val text: String,
        val emote: Emote?,
        val cheermote: Cheermote?,
        val mention: Mention?
    )

    @Serializable
    data class Emote(
        val id: String,

        @SerialName("emote_set_id")
        val emoteSetId: Int,

        @SerialName("owner_id")
        val ownerId: String,

        val format: List<String>,
    )

    @Serializable
    data class Cheermote(
        val prefix: String,
        val bits: Int,
        val tier: Int
    )

    @Serializable
    data class Mention(
        @SerialName("user_id")
        val userId: String,

        @SerialName("user_login")
        val userLogin: String,

        @SerialName("user_name")
        val userName: String
    )

    /**
     * The type of message.
     */
    @Serializable
    enum class Type {
        /**
         * A regular message sent by a user.
         */
        @SerialName("text")
        TEXT,

        /**
         * A highlighted message through channel points.
         */
        @SerialName("channel_points_highlighted")
        CHANNEL_POINTS_HIGHLIGHTED,

        /**
         * A message sent by a non-sub user in sub only chat through channel points.
         */
        @SerialName("channel_points_sub_only")
        CHANNEL_POINTS_SUB_ONLY,

        /**
         * A user's first message.
         */
        @SerialName("user_intro")
        USER_INTRO,

        /**
         * A power-up message effect.
         */
        @SerialName("power_ups_message_effect")
        POWER_UPS_MESSAGE_EFFECT,

        /**
         * A power-up emote that has been gigantified.
         */
        @SerialName("power_ups_gigantified_emote")
        POWER_UPS_GIGANTIFIED_EMOTE,
    }

    @Serializable
    data class Badge(
        @SerialName("set_id")
        val setId: String,

        val id: String,

        val info: String,
    )
}

@Serializable
data class Reply (
    @SerialName("parent_message_id")
    val parentMessageId: String,

    @SerialName("parent_message_body")
    val parentMessageBody: String,

    @SerialName("parent_user_id")
    val parentUserId: String,

    @SerialName("parent_user_login")
    val parentUserLogin: String,

    @SerialName("parent_user_name")
    val parentUserName: String,

    @SerialName("thread_message_id")
    val threadMessageId: String,

    @SerialName("thread_user_id")
    val threadUserId: String,

    @SerialName("thread_user_login")
    val threadUserLogin: String,

    @SerialName("thread_user_name")
    val threadUserName: String,
)
