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
 * This event is triggered when a message is held by Automod. See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#automodmessagehold)
 */
@Serializable
data class AutomodMessageHold(
    /**
     * The id of the broadcaster.
     */
    @SerialName("broadcaster_user_id")
    val broadcasterUserId: String,

    /**
     * The login of the broadcaster.
     */
    @SerialName("broadcaster_user_login")
    val broadcasterUserLogin: String,

    /**
     * The name of the broadcaster.
     */
    @SerialName("broadcaster_user_name")
    val broadcasterUserName: String,

    /**
     * The id of the user who sent the message.
     */
    @SerialName("user_id")
    val userId: String,

    /**
     * The login of the user who sent the message.
     */
    @SerialName("user_login")
    val userLogin: String,

    /**
     * The name of the user who sent the message.
     */
    @SerialName("user_name")
    val userName: String,

    /**
     * The id of the message flagged by the automod.
     */
    @SerialName("message_id")
    val messageId: String,

    /**
     * The body of the message that was flagged by the automod.
     */
    val message: Message,

    /**
     * The category of the message was flagged by the automod.
     */
    val category: String,

    /**
     * The level of severity. Measured between 1 and 4.
     */
    val level: Int,

    /**
     * When the message that was flagged by the automod.
     */
    @SerialName("held_at")
    @Serializable(LocalDateTimeRFC3339Serializer::class)
    val heldAt: LocalDateTime,
) : TwitchEvent {
    /**
     * This event is triggered when a message is held by Automod.
     *
     * Newer versions of AutomodMessageHold. See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#automodmessagehold-v2)
     *
     * @version 2
     */
    @Serializable
    data class V2(
        /**
         * The id of the broadcaster.
         */
        @SerialName("broadcaster_user_id")
        val broadcasterUserId: String,

        /**
         * The login of the broadcaster.
         */
        @SerialName("broadcaster_user_login")
        val broadcasterUserLogin: String,

        /**
         * The name of the broadcaster.
         */
        @SerialName("broadcaster_user_name")
        val broadcasterUserName: String,

        /**
         * The id of the user who sent the message.
         */
        @SerialName("user_id")
        val userId: String,

        /**
         * The login of the user who sent the message.
         */
        @SerialName("user_login")
        val userLogin: String,

        /**
         * The name of the user who sent the message.
         */
        @SerialName("user_name")
        val userName: String,

        /**
         * The id of the message flagged by the automod.
         */
        @SerialName("message_id")
        val messageId: String,

        /**
         * The body of the message that was flagged by the automod.
         */
        val message: Message,

        /**
         * When the message was flagged by the automod.
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
