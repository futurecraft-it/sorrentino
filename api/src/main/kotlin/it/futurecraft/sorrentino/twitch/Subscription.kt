package it.futurecraft.sorrentino.twitch

import it.futurecraft.sorrentino.utils.LocalDateTimeRFC3339Serializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The object representing a subscription to Twitch's EventSub.
 * See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-reference/#subscription).
 */
@Serializable
data class Subscription(
    /**
     * The ID of the client that subscribed to the event.
     */
    val id: String,

    /**
     * The notification's subscription type.
     */
    val type: Type,

    /**
     * The version of the subscription.
     */
    val version: String,

    /**
     * The status of the subscription.
     */
    val status: Status,

    /**
     * How much the subscription counts against your limit.
     */
    val cost: Int,

    /**
     * The time the notification was created.
     */
    @SerialName("created_at")
    @Serializable(LocalDateTimeRFC3339Serializer::class)
    val createdAt: LocalDateTime,

    /**
     * Subscription-specific parameters.
     */
    val condition: JsonElement,

    /**
     * The transport method used to deliver the notification.
     */
    val transport: Transport,
) {
    /**
     * The status of the subscription.
     */
    enum class Status {
        /**
         * The subscription is enabled and active.
         */
        @SerialName("enabled")
        ENABLED,

        /**
         * The subscription callback has the verification pending.
         */
        @SerialName("webhook_callback_verification_pending")
        WEBHOOK_CALLBACK_VERIFICATION_PENDING,

        /**
         * The subscription is disabled.
         */
        @SerialName("user_removed")
        USER_REMOVED,

        /**
         * The authorization to the subscription has been revoked.
         */
        @SerialName("authorization_revoked")
        AUTHORIZATION_REVOKED,

        /**
         * The notification to the subscription has failed too many times.
         */
        @SerialName("notification_failure_exceeded")
        NOTIFICATION_FAILURE_EXCEEDED,

        /**
         * The version of the subscription has been removed.
         */
        @SerialName("version_removed")
        VERSION_REMOVED
    }

    /**
     * The notification's subscription type.
     */
    enum class Type {
        @SerialName("automod.message.hold")
        AUTOMOD_MESSAGE_HOLD,

        @SerialName("automod.message.update")
        AUTOMOD_MESSAGE_UPDATE,

        @SerialName("automod.settings.update")
        AUTOMOD_SETTINGS_UPDATE,

        @SerialName("automod.terms.update")
        AUTOMOD_TERMS_UPDATE,

        @SerialName("channel.bits.use")
        CHANNEL_BITS_USE,

        @SerialName("channel.update")
        CHANNEL_UPDATE,

        @SerialName("channel.follow")
        CHANNEL_FOLLOW,

        @SerialName("channel.ad_break.begin")
        CHANNEL_AD_BREAK_BEGIN,

        @SerialName("channel.chat.clear")
        CHANNEL_CHAT_CLEAR,

        @SerialName("channel.chat.clear_user_messages")
        CHANNEL_CHAT_CLEAR_USER_MESSAGES,

        @SerialName("channel.chat.message")
        CHANNEL_CHAT_MESSAGE,

        @SerialName("channel.chat.message_delete")
        CHANNEL_CHAT_MESSAGE_DELETE,

        @SerialName("channel.chat.notification")
        CHANNEL_CHAT_NOTIFICATION,

        @SerialName("channel.chat_settings.update")
        CHANNEL_CHAT_SETTINGS_UPDATE,

        @SerialName("channel.chat.user_message_hold")
        CHANNEL_CHAT_USER_MESSAGE_HOLD,

        @SerialName("channel.chat.user_message_update")
        CHANNEL_CHAT_USER_MESSAGE_UPDATE,

        @SerialName("channel.shared_chat.begin")
        CHANNEL_SHARED_CHAT_BEGIN,

        @SerialName("channel.shared_chat.update")
        CHANNEL_SHARED_CHAT_UPDATE,

        @SerialName("channel.shared_chat.end")
        CHANNEL_SHARED_CHAT_END,

        @SerialName("channel.subscribe")
        CHANNEL_SUBSCRIBE,

        @SerialName("channel.subscription.end")
        CHANNEL_SUBSCRIPTION_END,

        @SerialName("channel.subscription.gift")
        CHANNEL_SUBSCRIPTION_GIFT,

        @SerialName("channel.subscription.message")
        CHANNEL_SUBSCRIPTION_MESSAGE,

        @SerialName("channel.cheer")
        CHANNEL_CHEER,

        @SerialName("channel.raid")
        CHANNEL_RAID,

        @SerialName("channel.ban")
        CHANNEL_BAN,

        @SerialName("channel.unban")
        CHANNEL_UNBAN,

        @SerialName("channel.unban_request.create")
        CHANNEL_UNBAN_REQUEST_CREATE,

        @SerialName("channel.unban_request.resolve")
        CHANNEL_UNBAN_REQUEST_RESOLVE,

        @SerialName("channel.moderate")
        CHANNEL_MODERATE,

        @SerialName("channel.moderator.add")
        CHANNEL_MODERATOR_ADD,

        @SerialName("channel.moderator.remove")
        CHANNEL_MODERATOR_REMOVE,

        @SerialName("channel.guest_star_session.begin")
        CHANNEL_GUEST_STAR_SESSION_BEGIN,

        @SerialName("channel.guest_star_session.end")
        CHANNEL_GUEST_STAR_SESSION_END,

        @SerialName("channel.guest_star_guest.update")
        CHANNEL_GUEST_STAR_GUEST_UPDATE,

        @SerialName("channel.guest_star_settings.update")
        CHANNEL_GUEST_STAR_SETTINGS_UPDATE,

        @SerialName("channel.channel_points_automatic_reward_redemption.add")
        CHANNEL_CHANNEL_POINTS_AUTOMATIC_REWARD_REDEMPTION_ADD,

        @SerialName("channel.channel_points_custom_reward.add")
        CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_ADD,

        @SerialName("channel.channel_points_custom_reward.update")
        CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_UPDATE,

        @SerialName("channel.channel_points_custom_reward.remove")
        CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REMOVE,

        @SerialName("channel.channel_points_custom_reward_redemption.add")
        CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_ADD,

        @SerialName("channel.channel_points_custom_reward_redemption.update")
        CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_UPDATE,

        @SerialName("channel.poll.begin")
        CHANNEL_POLL_BEGIN,

        @SerialName("channel.poll.progress")
        CHANNEL_POLL_PROGRESS,

        @SerialName("channel.poll.end")
        CHANNEL_POLL_END,

        @SerialName("channel.prediction.begin")
        CHANNEL_PREDICTION_BEGIN,

        @SerialName("channel.prediction.progress")
        CHANNEL_PREDICTION_PROGRESS,

        @SerialName("channel.prediction.lock")
        CHANNEL_PREDICTION_LOCK,

        @SerialName("channel.prediction.end")
        CHANNEL_PREDICTION_END,

        @SerialName("channel.suspicious_user.message")
        CHANNEL_SUSPICIOUS_USER_MESSAGE,

        @SerialName("channel.suspicious_user.update")
        CHANNEL_SUSPICIOUS_USER_UPDATE,

        @SerialName("channel.vip.add")
        CHANNEL_VIP_ADD,

        @SerialName("channel.vip.remove")
        CHANNEL_VIP_REMOVE,

        @SerialName("channel.warning.acknowledge")
        CHANNEL_WARNING_ACKNOWLEDGE,

        @SerialName("channel.warning.send")
        CHANNEL_WARNING_SEND,

        @SerialName("channel.charity_campaign.donate")
        CHANNEL_CHARITY_CAMPAIGN_DONATE,

        @SerialName("channel.charity_campaign.start")
        CHANNEL_CHARITY_CAMPAIGN_START,

        @SerialName("channel.charity_campaign.progress")
        CHANNEL_CHARITY_CAMPAIGN_PROGRESS,

        @SerialName("channel.charity_campaign.stop")
        CHANNEL_CHARITY_CAMPAIGN_STOP,

        @SerialName("conduit.shard.disabled")
        CONDUIT_SHARD_DISABLED,

        @SerialName("drop.entitlement.grant")
        DROP_ENTITLEMENT_GRANT,

        @SerialName("extension.bits_transaction.create")
        EXTENSION_BITS_TRANSACTION_CREATE,

        @SerialName("channel.goal.begin")
        CHANNEL_GOAL_BEGIN,

        @SerialName("channel.goal.progress")
        CHANNEL_GOAL_PROGRESS,

        @SerialName("channel.goal.end")
        CHANNEL_GOAL_END,

        @SerialName("channel.hype_train.begin")
        CHANNEL_HYPE_TRAIN_BEGIN,

        @SerialName("channel.hype_train.progress")
        CHANNEL_HYPE_TRAIN_PROGRESS,

        @SerialName("channel.hype_train.end")
        CHANNEL_HYPE_TRAIN_END,

        @SerialName("channel.shield_mode.begin")
        CHANNEL_SHIELD_MODE_BEGIN,

        @SerialName("channel.shield_mode.end")
        CHANNEL_SHIELD_MODE_END,

        @SerialName("channel.shoutout.create")
        CHANNEL_SHOUTOUT_CREATE,

        @SerialName("channel.shoutout.receive")
        CHANNEL_SHOUTOUT_RECEIVE,

        @SerialName("stream.online")
        STREAM_ONLINE,

        @SerialName("stream.offline")
        STREAM_OFFLINE,

        @SerialName("user.authorization.grant")
        USER_AUTHORIZATION_GRANT,

        @SerialName("user.authorization.revoke")
        USER_AUTHORIZATION_REVOKE,

        @SerialName("user.update")
        USER_UPDATE,

        @SerialName("user.whisper.message")
        USER_WHISPER_MESSAGE
    }

    /**
     * The transport method used to deliver the notification.
     */
    @Serializable
    data class Transport(
        /**
         * The method used to deliver the notification.
         */
        val method: String,

        /**
         * The callback URL to which the notification is sent.
         */
        val callback: String,

        /**
         * The secret used to sign the notification.
         */
        val secret: String? = null
    )
}
