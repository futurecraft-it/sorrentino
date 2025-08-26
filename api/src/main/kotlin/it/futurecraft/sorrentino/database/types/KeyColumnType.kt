package it.futurecraft.sorrentino.database.types

import net.kyori.adventure.key.Key
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ColumnType
import org.jetbrains.exposed.v1.core.Table

class KeyColumnType : ColumnType<Key>() {
    override fun sqlType(): String = "VARCHAR(512)"

    override fun valueFromDB(value: Any): Key = when (value) {
        is String -> {
            val fields = value.split(":")
            Key.key(fields[0], fields[1])
        }
        else -> error("Unexpected value of type Key: $value of ${value::class.qualifiedName}")
    }

    override fun nonNullValueToString(value: Key): String {
        return value.toString()
    }

    override fun nonNullValueAsDefaultString(value: Key): String {
        return "`${nonNullValueToString(value)}`"
    }
}

fun Table.key(name: String): Column<Key> = registerColumn(name, KeyColumnType())
