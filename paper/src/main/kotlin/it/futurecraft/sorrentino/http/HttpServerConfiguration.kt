package it.futurecraft.sorrentino.http

import kotlinx.serialization.Serializable

@Serializable
data class HttpServerConfiguration(
    val port: Int = 3000,
    val host: String = "127.0.0.1",
)
