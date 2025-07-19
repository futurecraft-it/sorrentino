package it.futurecraft.sorrentino.twitch.events.channel.chat

import it.futurecraft.sorrentino.twitch.Cheer
import it.futurecraft.sorrentino.twitch.Message
import it.futurecraft.sorrentino.twitch.Reply
import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Any user sends a message to a specific chat room.
 * See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelchatmessage)
 */
@Serializable
data class ChannelChatMessage(
    /**
     * The broadcaster’s user ID.
     */
    @SerialName("broadcaster_user_id")
    val broadcasterUserId: String,

    /**
     * The login for the channel.
     */
    @SerialName("broadcaster_user_login")
    val broadcasterUserLogin: String,

    /**
     * The display name for the channel.
     */
    @SerialName("broadcaster_user_name")
    val broadcasterUserName: String,

    /**
     * The user ID of the user that sent the message.
     *
     */
    @SerialName("chatter_user_id")
    val chatterUserId: String,

    /**
     * The user login of the user that sent the message.
     */
    @SerialName("chatter_user_login")
    val chatterUserLogin: String,

    /**
     * The username of the user that sent the message.
     */
    @SerialName("chatter_user_name")
    val chatterUserName: String,

    /**
     * The id of message that was sent.
     */
    val messageId: String,

    /**
     * The message that was sent.
     */
    val message: Message,

    /**
     * The type of message.
     */
    @SerialName("message_type")
    val messageType: Message.Type,

    /**
     * List of chat badges.
     */
    val badges: List<Message.Badge>,

    /**
     * Optional. Metadata if this message is a cheer.
     */
    val cheer: Cheer?,

    /**
     * The color of the user’s name in the chat room. This is a hexadecimal RGB color code in the form, `#<RGB>`. This tag may be empty if it is never set.
     */
    val color: String,

    /**
     * Optional. Metadata if this message is a reply.
     */
    val reply: Reply?,

    /**
     * Optional. The ID of a channel points custom reward that was redeemed.
     */
    @SerialName("channel_points_custom_reward_id")
    val channelPointsCustomRewardId: String?,

    /**
     * Optional. The broadcaster user ID of the channel the message was sent from. Is null when the message happens in the same channel as the broadcaster. Is not null when in a shared chat session, and the action happens in the channel of a participant other than the broadcaster.
     */
    @SerialName("source_broadcaster_user_id")
    val sourceBroadcasterUserId: String?,

    /**
     * Optional. The login of the broadcaster of the channel the message was sent from. Is null when the message happens in the same channel as the broadcaster. Is not null when in a shared chat session, and the action happens in the channel of a participant other than the broadcaster.
     */
    @SerialName("source_broadcaster_user_login")
    val sourceBroadcasterUserLogin: String?,

    /**
     * Optional. The username of the broadcaster of the channel the message was sent from. Is null when the message happens in the same channel as the broadcaster. Is not null when in a shared chat session, and the action happens in the channel of a participant other than the broadcaster.
     */
    @SerialName("source_broadcaster_user_name")
    val sourceBroadcasterUserName: String?,

    /**
     * Optional. The UUID that identifies the source message from the channel the message was sent from. Is null when the message happens in the same channel as the broadcaster. Is not null when in a shared chat session, and the action happens in the channel of a participant other than the broadcaster.
     */
    @SerialName("source_message_id")
    val sourceMessageId: String?,

    /**
     * Optional. The list of chat badges for the chatter in the channel the message was sent from. Is null when the message happens in the same channel as the broadcaster. Is not null when in a shared chat session, and the action happens in the channel of a participant other than the broadcaster.
     */
    @SerialName("source_badges")
    val sourceBadges: List<Message.Badge>?,

    /**
     * Optional. Determines if a message delivered during a shared chat session is only sent to the source channel. Has no effect if the message is not sent during a shared chat session.
     */
    @SerialName("is_source_only")
    val sourceOnly: Boolean?,
) : TwitchEvent
