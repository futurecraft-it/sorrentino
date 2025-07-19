package it.futurecraft.sorrentino.twitch.events.automod

import it.futurecraft.sorrentino.twitch.Automod
import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A notification is sent when a broadcaster’s automod terms are updated. Changes to private terms are not sent.
 * [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#automodtermsupdate)
 */
@Serializable
data class AutomodTermsUpdate(
    /**
     * The ID of the broadcaster specified in the request.
     */
    @SerialName("broadcaster_user_id")
    val broadcasterUserId: String,

    /**
     * The login of the broadcaster specified in the request.
     */
    @SerialName("broadcaster_user_login")
    val broadcasterUserLogin: String,

    /**
     * The display name of the broadcaster specified in the request.
     */
    @SerialName("broadcaster_user_name")
    val broadcasterUserName: String,

    /**
     * The ID of the moderator who updated the automod terms.
     */
    @SerialName("moderator_user_id")
    val moderatorUserId: String,

    /**
     * The login of the moderator who updated the automod terms.
     */
    @SerialName("moderator_user_login")
    val moderatorUserLogin: String,

    /**
     * The display name of the moderator who updated the automod terms.
     */
    @SerialName("moderator_user_name")
    val moderatorUserName: String,

    /**
     * The status change applied to the terms.
     */
    val action: Automod.Action,

    /**
     * Indicates whether this term was added due to an Automod message approve/deny action.
     */
    @SerialName("from_automod")
    val automod: Boolean,

    /**
     * The list of terms that had a status change.
     */
    val terms: List<String>
) : TwitchEvent
