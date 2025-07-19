package it.futurecraft.sorrentino.twitch.events.stream

import it.futurecraft.sorrentino.twitch.StreamType
import it.futurecraft.sorrentino.twitch.events.TwitchEvent
import it.futurecraft.sorrentino.utils.LocalDateTimeRFC3339Serializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamOnlineEvent (
    val id: String,

    @SerialName("user_id")
    val userId: String,

    @SerialName("user_login")
    val userLogin: String,

    @SerialName("user_name")
    val userName: String,

    val type: StreamType,

    @SerialName("started_at")
    @Serializable(LocalDateTimeRFC3339Serializer::class)
    val startedAt: LocalDateTime,
) : TwitchEvent

