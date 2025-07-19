package it.futurecraft.sorrentino.twitch.events.channel

import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import kotlinx.serialization.SerialName

data class ChannelFollowEvent(
    @SerialName("user_id")
    val userId: String,

    @SerialName("user_name")
    val userName: String,

    @SerialName("user_login")
    val userLogin: String,

    @SerialName("broadcaster_user_id")
    val broadcasterUserId: String,

    @SerialName("broadcaster_user_name")
    val broadcasterUserName: String,

    @SerialName("broadcaster_user_login")
    val broadcasterUserLogin: String,
) : TwitchEvent