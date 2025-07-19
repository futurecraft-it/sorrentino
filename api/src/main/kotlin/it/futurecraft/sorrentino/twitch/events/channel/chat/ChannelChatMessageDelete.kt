package it.futurecraft.sorrentino.twitch.events.channel.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderator has removed a specific message.
 * See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelchatmessage_delete)
 */
@Serializable
data class ChannelChatMessageDelete(
    /**
     * The broadcaster’s user ID.
     */
    @SerialName("broadcaster_user_id")
    val broadcasterUserId: String,

    /**
     * The display name for the channel.
     */
    @SerialName("broadcaster_user_name")
    val broadcasterUserName: String,

    /**
     * The login for the channel.
     */
    @SerialName("broadcaster_user_login")
    val broadcasterUserLogin: String,

    /**
     * The user ID of the user that sent the message.
     */
    @SerialName("target_user_id")
    val targetUserId: String,

    /**
     * The user login of the user that sent the message.
     */
    @SerialName("target_user_name")
    val targetUserName: String,

    /**
     * The username of the user that sent the message.
     */
    @SerialName("target_user_login")
    val targetUserLogin: String,

    /**
     * The id of message that was deleted.
     */
    @SerialName("message_id")
    val messageId: String
)
