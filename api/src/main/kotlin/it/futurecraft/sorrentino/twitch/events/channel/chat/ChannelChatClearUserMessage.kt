package it.futurecraft.sorrentino.twitch.events.channel.chat

import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderator or bot has cleared all messages from a specific user.
 * See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelchatclear_user_messages)
 */
@Serializable
data class ChannelChatClearUserMessage(
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
     * The ID of the user that was banned or put in a timeout. All of their messages are deleted.
     */
    @SerialName("target_user_id")
    val targetUserId: String,

    /**
     * The user login of the user that was banned or put in a timeout.
     */
    @SerialName("target_user_login")
    val targetUserLogin: String,

    /**
     * The username of the user that was banned or put in a timeout.
     */
    @SerialName("target_user_name")
    val targetUserName: String,
) : TwitchEvent
