package it.futurecraft.sorrentino.configuration.formatters

import it.futurecraft.sorrentino.configuration.Formatter
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
object JsonFormatter : Formatter() {
    override val format: SerialFormat
        get() = _json

    private val _json = Json { prettyPrint = true }

    override fun <T> serialize(data: T, serializer: SerializationStrategy<T>): String =
        _json.encodeToString(serializer, data)

    override fun <T> deserialize(file: File, deserializer: DeserializationStrategy<T>): T = file.run {
        _json.decodeFromStream(deserializer, inputStream())
    }
}