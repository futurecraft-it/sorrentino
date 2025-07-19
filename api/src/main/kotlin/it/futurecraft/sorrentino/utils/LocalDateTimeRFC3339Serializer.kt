package it.futurecraft.sorrentino.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.toInstant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@OptIn(FormatStringsInDatetimeFormats::class)
internal val RFC3339_FORMAT = DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET

object LocalDateTimeRFC3339Serializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("futurecraft.utils.LocalDateTime/RFC3339", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val date = RFC3339_FORMAT.parse(decoder.decodeString())
        return date.toLocalDateTime()
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val instant = value.toInstant(TimeZone.UTC)
        encoder.encodeString(instant.format(RFC3339_FORMAT))
    }

    override fun toString(): String = "futurecraft.utils.LocalDateTime/RFC3339"
}