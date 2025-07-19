package it.futurecraft.sorrentino.twitch.events.channel.chat

import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A moderator or bot has cleared all messages from the chat room.
 * See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelchatclear)
 */
@Serializable
data class ChannelChatClear(
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
) : TwitchEvent