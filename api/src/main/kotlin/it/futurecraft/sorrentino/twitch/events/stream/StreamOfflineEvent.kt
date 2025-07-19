package it.futurecraft.sorrentino.twitch.events.stream

import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamOfflineEvent(
    @SerialName("broadcaster_user_id")
    val broadcasterUserId: String,

    @SerialName("broadcaster_user_name")
    val broadcasterUserName: String,

    @SerialName("broadcaster_user_login")
    val broadcasterUserLogin: String,
) : TwitchEvent
