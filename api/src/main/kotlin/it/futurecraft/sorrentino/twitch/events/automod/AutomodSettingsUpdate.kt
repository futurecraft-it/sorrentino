package it.futurecraft.sorrentino.twitch.events.automod

import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A notification is sent when a broadcaster’s automod settings are updated.
 * See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#automodsettingsupdate)
 */
@Serializable
data class AutomodSettingsUpdate(
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
     * The ID of the moderator who changed the channel settings.
     */
    @SerialName("moderator_user_id")
    val moderatorUserId: String,

    /**
     * The login of the moderator who changed the channel settings.
     */
    @SerialName("moderator_user_login")
    val moderatorUserLogin: String,

    /**
     * The display name of the moderator who changed the channel settings.
     */
    @SerialName("moderator_user_name")
    val moderatorUserName: String,

    /**
     * The Automod level for hostility involving name-calling or insults.
     */
    val bullying: Int,

    /**
     * The default AutoMod level for the broadcaster. This field is null if the broadcaster has set one or more of the individual settings.
     */
    @SerialName("overall_level")
    val overallLevel: Int?,

    /**
     * The Automod level for discrimination against disability.
     */
    val disability: Int,

    /**
     * The Automod level for racial discrimination.
     */
    @SerialName("race_ethnicity_or_religion")
    val ethnicity: Int,

    /**
     * The Automod level for discrimination against women.
     */
    val misogyny: Int,

    /**
     * The AutoMod level for discrimination based on sexuality, sex, or gender.
     */
    @SerialName("sexuality_sex_or_gender")
    val gender: Int,

    /**
     * The Automod level for hostility involving aggression.
     */
    val aggression: Int,

    /**
     * The Automod level for sexual content.
     */
    @SerialName("sex_based_terms")
    val sexBasedTerms: Int,

    /**
     * The Automod level for profanity.
     */
    val swearing: Int,
) : TwitchEvent
