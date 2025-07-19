package it.futurecraft.sorrentino.twitch.events.channel

import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import it.futurecraft.sorrentino.utils.LocalDateTimeRFC3339Serializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A midroll commercial break has started running.
 * See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelad_breakbegin) for more information.
 */
@Serializable
data class ChannelAdBreakBegin(
    /**
     * The duration of the ad break in seconds.
     */
    @SerialName("duration_seconds")
    val duration: Int,

    /**
     * When the ad break started.
     */
    @SerialName("started_at")
    @Serializable(with = LocalDateTimeRFC3339Serializer::class)
    val startedAt: LocalDateTime,

    /**
     * Whether the ad break was requested by the broadcaster or automatically triggered by Twitch.
     */
    @SerialName("is_automatic")
    val automatic: Boolean,

    /**
     * The broadcaster’s user ID for the channel the ad was run on.
     */
    @SerialName("broadcaster_user_id")
    val broadcasterUserId: String,

    /**
     * The login for the channel the ad was run on.
     */
    @SerialName("broadcaster_user_login")
    val broadcasterUserLogin: String,

    /**
     * The display name for the channel the ad was run on.
     */
    @SerialName("broadcaster_user_name")
    val broadcasterUserName: String,

    /**
     * 	The ID of the user that requested the ad. For automatic ads, this will be the ID of the broadcaster.
     */
    @SerialName("requester_user_id")
    val requesterUserId: String,

    /**
     * The login of the user that requested the ad.
     */
    @SerialName("requester_user_login")
    val requesterUserLogin: String,

    /**
     * The display name of the user that requested the ad.
     */
    @SerialName("requester_user_name")
    val requesterUserName: String,
) : TwitchEvent