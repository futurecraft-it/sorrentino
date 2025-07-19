package it.futurecraft.sorrentino.twitch.events.channel

import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import it.futurecraft.sorrentino.utils.LocalDateTimeRFC3339Serializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A viewer is banned from the specified channel.
 * See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelban)
 */
@Serializable
data class ChannelBanEvent(
    /**
     * The user ID for the user who was banned on the specified channel.
     */
    @SerialName("user_id")
    val userId: String,

    /**
     * The login for the user who was banned on the specified channel.
     */
    @SerialName("user_login")
    val userLogin: String,

    /**
     * The display name for the user who was banned on the specified channel.
     */
    @SerialName("user_name")
    val userName: String,

    /**
     * The user ID for the broadcaster of the channel the user was banned on.
     */
    @SerialName("broadcaster_user_id")
    val broadcasterUserId: String,

    /**
     * The login for the broadcaster of the channel the user was banned on.
     */
    @SerialName("broadcaster_user_login")
    val broadcasterUserLogin: String,

    /**
     * The display name for the broadcaster of the channel the user was banned on.
     */
    @SerialName("broadcaster_user_name")
    val broadcasterUserName: String,

    /**
     * The user ID for the moderator who banned the user.
     */
    @SerialName("moderator_user_id")
    val moderatorUserId: String,

    /**
     * The login for the moderator who banned the user.
     */
    @SerialName("moderator_user_login")
    val moderatorUserLogin: String,

    /**
     * The display name for the moderator who banned the user.
     */
    @SerialName("moderator_user_name")
    val moderatorUserName: String,

    /**
     * The reason the user was banned.
     */
    val reason: String,

    /**
     * When the user was banned.
     */
    @SerialName("banned_at")
    @Serializable(with = LocalDateTimeRFC3339Serializer::class)
    val bannedAt: LocalDateTime,

    /**
     * When the ban ends, if it is temporary (timeout).
     */
    @SerialName("ends_at")
    @Serializable(with = LocalDateTimeRFC3339Serializer::class)
    val endsAt: LocalDateTime?,

    /**
     * Whether the ban is permanent or temporary.
     */
    @SerialName("is_permanent")
    val permanent: Boolean
) : TwitchEvent
