package it.futurecraft.sorrentino.configuration

import it.futurecraft.sorrentino.extensions.JFile
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer

/**
 * Base formatter for a file. It can serialize and deserialize a file in the specified format.
 *
 * Note that these methods are run synchronously by default so you shall launch a coroutine in order to work asynchronously on files.
 */
abstract class Formatter {
    abstract val format: SerialFormat

    /**
     * Serialize an object to its formatted string.
     *
     * @param data The object to serialize.
     * @param serializer The serializer of the object's class.
     * @return The serialized data.
     */
    abstract fun <T> serialize(data: T, serializer: SerializationStrategy<T>): String

    /**
     * Serialize an object to its formatted string.
     *
     * @param data The object to serialize.
     * @return The serialized data.
     */
    inline fun <reified T> serialize(data: T): String =
        serialize(data, format.serializersModule.serializer())

    /**
     * Deserialize a file to its result object.
     *
     * @param file The file to deserialize.
     * @param deserializer The deserializer of the result object's class.
     * @return The result object.
     */
    abstract fun <T> deserialize(file: JFile, deserializer: DeserializationStrategy<T>): T

    /**
     * Deserialize a file to its result object.
     *
     * @param file The file to deserialize.
     * @return The result object.
     */
    inline fun <reified T> deserialize(file: JFile): T =
        deserialize(file, format.serializersModule.serializer())
}