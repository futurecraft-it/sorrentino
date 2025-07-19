package it.futurecraft.sorrentino.twitch

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface Bits {
    @Serializable
    enum class Type {
        /**
         * A user has used Bits to cheer in a channel.
         */
        @SerialName("cheer")
        CHEER,

        /**
         * A user has used Bits to power up a channel.
         */
        @SerialName("power_up")
        POWER_UP
    }

    @Serializable
    data class PowerUp(
        val type: Type,

        /**
         * Optional. Emote associated with the reward.
         */
        val emote: Emote?,

        /**
         * Optional. The ID of the message effect.
         */
        @SerialName("message_effect_id")
        val effectId: String?,
    ) {
        @Serializable
        enum class Type {
            @SerialName("message_effect")
            MESSAGE_EFFECT,

            @SerialName("celebration")
            CELEBRATION,

            @SerialName("gigantify_an_emote")
            GIGANTIFY_AN_EMOTE,
        }

        @Serializable
        data class Emote(
            /**
             * The ID that uniquely identifies this emote.
             */
            val id: String,

            /**
             * The human-readable emote token.
             */
            val name: String,
        )
    }
}