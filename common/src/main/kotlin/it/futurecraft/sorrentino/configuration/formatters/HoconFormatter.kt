package it.futurecraft.sorrentino.configuration.formatters

import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import it.futurecraft.sorrentino.configuration.Formatter
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.hocon.Hocon
import java.io.File

@OptIn(ExperimentalSerializationApi::class)
object HoconFormatter : Formatter() {
    override val format: SerialFormat
        get() = _hocon

    private val _hocon = Hocon { encodeDefaults = true }

    override fun <T> serialize(data: T, serializer: SerializationStrategy<T>): String {
        val config = _hocon.encodeToConfig(serializer, data)
        return config.root().render(ConfigRenderOptions.concise().setFormatted(true).setJson(false))
    }

    override fun <T> deserialize(file: File, deserializer: DeserializationStrategy<T>): T = file.run {
        val config = ConfigFactory.parseFile(this)
        _hocon.decodeFromConfig(deserializer, config)
    }
}