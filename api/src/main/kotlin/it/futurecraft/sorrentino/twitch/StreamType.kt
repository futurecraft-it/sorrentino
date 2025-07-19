package it.futurecraft.sorrentino.twitch

import kotlinx.serialization.Serializable

@Serializable
enum class StreamType {
    LIVE,
    RERUN,
    PLAYLIST,
    PREMIERE,
    WATCH_PARTY,
}