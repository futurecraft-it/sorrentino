package it.futurecraft.sorrentino.twitch.events.automod

import it.futurecraft.sorrentino.twitch.Automod
import it.futurecraft.sorrentino.twitch.BlockedTerm
import it.futurecraft.sorrentino.twitch.Message
import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import it.futurecraft.sorrentino.utils.LocalDateTimeRFC3339Serializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This event is triggered when s message in the automod queue had its status changed. See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#automodmessageupdate)
 */
@Serializable
data class AutomodMessageUpdate(
    /**
     * The ID of the broadcaster whose channel the message was sent in.
     */
    @SerialName("broadcaster_user_id")
    val broadcasterUserId: String,

    /**
     * The login of the broadcaster whose channel the message was sent in.
     */
    @SerialName("broadcaster_user_login")
    val broadcasterUserLogin: String,

    /**
     * The display name of the broadcaster whose channel the message was sent in.
     */
    @SerialName("broadcaster_user_name")
    val broadcasterUserName: String,

    /**
     * The ID of the user who sent the message.
     */
    @SerialName("user_id")
    val userId: String,

    /**
     * The login of the user who sent the message.
     */
    @SerialName("user_login")
    val userLogin: String,

    /**
     * The display name of the user who sent the message.
     */
    @SerialName("user_name")
    val userName: String,

    /**
     * The ID of the moderator who updated the message.
     */
    @SerialName("moderator_user_id")
    val moderatorUserId: String,

    /**
     * The login of the moderator who updated the message.
     */
    @SerialName("moderator_user_login")
    val moderatorUserLogin: String,

    /**
     * The display name of the moderator who updated the message.
     */
    @SerialName("moderator_user_name")
    val moderatorUserName: String,

    /**
     * The ID of the message that was updated.
     */
    @SerialName("message_id")
    val messageId: String,

    /**
     * The message that was updated.
     */
    val message: Message,

    /**
     * The category of the message.
     */
    val category: String,

    /**
     * The status of the message.
     */
    val status: Automod.Status,

    /**
     * The level of severity. Measured between 1 and 4.
     */
    val level: Int,

    /**
     * The reason for the update.
     */
    @SerialName("held_at")
    @Serializable(LocalDateTimeRFC3339Serializer::class)
    val heldAt: LocalDateTime,
) : TwitchEvent {

    /**
     * A message in the automod queue had its status changed. Only public blocked terms trigger notifications, not private ones.
     * See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#automodmessageupdate-v2)
     *
     * @version 2
     */
    @Serializable
    data class V2(
        /**
         * The ID of the broadcaster whose channel the message was sent in.
         */
        @SerialName("broadcaster_user_id")
        val broadcasterUserId: String,

        /**
         * The login of the broadcaster whose channel the message was sent in.
         */
        @SerialName("broadcaster_user_login")
        val broadcasterUserLogin: String,

        /**
         * The display name of the broadcaster whose channel the message was sent in.
         */
        @SerialName("broadcaster_user_name")
        val broadcasterUserName: String,

        /**
         * The ID of the user who sent the message.
         */
        @SerialName("user_id")
        val userId: String,

        /**
         * The login of the user who sent the message.
         */
        @SerialName("user_login")
        val userLogin: String,

        /**
         * The display name of the user who sent the message.
         */
        @SerialName("user_name")
        val userName: String,

        /**
         * The ID of the moderator who updated the message.
         */
        @SerialName("moderator_user_id")
        val moderatorUserId: String,

        /**
         * The login of the moderator who updated the message.
         */
        @SerialName("moderator_user_login")
        val moderatorUserLogin: String,

        /**
         * The display name of the moderator who updated the message.
         */
        @SerialName("moderator_user_name")
        val moderatorUserName: String,

        /**
         * The ID of the message that was updated.
         */
        @SerialName("message_id")
        val messageId: String,

        /**
         * The message that was updated.
         */
        val message: Message,

        /**
         * The message’s status.
         */
        val status: Automod.Status,


        /**
         * When the message was flagged.
         */
        @SerialName("held_at")
        @Serializable(LocalDateTimeRFC3339Serializer::class)
        val heldAt: LocalDateTime,

        /**
         * Whether the message was held by the automod or for a blocked term.
         */
        val reason: Automod.Reason,

        /**
         * Optional. If the message was caught by automod, this will be populated.
         */
        val automod: Automod?,

        /**
         * Optional. If the message was caught due to a blocked term, this will be populated.
         */
        @SerialName("blocked_term")
        val blockedTerm: BlockedTerm?,
    ) : TwitchEvent
}
