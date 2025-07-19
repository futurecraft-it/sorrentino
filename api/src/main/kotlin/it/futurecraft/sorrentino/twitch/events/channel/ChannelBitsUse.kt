package it.futurecraft.sorrentino.twitch.events.channel

import it.futurecraft.sorrentino.twitch.Bits
import it.futurecraft.sorrentino.twitch.Message
import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A notification is sent whenever Bits are used on a channel.
 * See [Twitch Documentation](https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types/#channelbitsuse).
 */
@Serializable
data class ChannelBitsUse(
    /**
     * The User ID of the channel where the Bits were redeemed.
     */
    @SerialName("broadcaster_user_id")
    val broadcasterUserId: String,

    /**
     * The login of the channel where the Bits were redeemed.
     */
    @SerialName("broadcaster_user_login")
    val broadcasterUserLogin: String,

    /**
     * The display name of the channel where the Bits were redeemed.
     */
    @SerialName("broadcaster_user_name")
    val broadcasterUserName: String,

    /**
     * The ID of the user who used the Bits.
     */
    @SerialName("user_id")
    val userId: String,

    /**
     * The login of the user who used the Bits.
     */
    @SerialName("user_login")
    val userLogin: String,

    /**
     * The display name of the user who used the Bits.
     */
    @SerialName("user_name")
    val userName: String,

    /**
     * The amount of Bits used.
     */
    val bits: Int,

    /**
     * The type of Bits used.
     */
    val type: Bits.Type,

    /**
     * Optional. An object that contains the user message and emote information needed to recreate the message.
     */
    val message: Message?,

    /**
     * Optional. Data about Power-up.
     */
    val powerUp: Bits.PowerUp?,
) : TwitchEvent
