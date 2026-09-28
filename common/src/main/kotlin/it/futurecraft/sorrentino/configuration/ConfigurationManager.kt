package it.futurecraft.sorrentino.configuration

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.div

import it.futurecraft.sorrentino.extensions.file
import it.futurecraft.sorrentino.extensions.exists

class ConfigurationManagerImpl(private val _folder: Path): ConfigurationManager {
    private val _cache = ConcurrentHashMap<File.Key<*>, File.Schema>()

    override val empty: Boolean
        get() = _cache.isEmpty()

    override suspend operator fun <S : File.Schema> set(key: File.Key<S>, data: S): Boolean = withContext(Dispatchers.IO) {
        val cached = _cache[key]

        if (cached != null && cached == data) return@withContext false

        _cache[key] = data

        val path = _folder / key.file.path
        val serializer = key.file.serializer

        val formatter = key.file.format.formatter

        path.file.run {
            if (!exists) parentFile.mkdirs()

            val content = formatter.serialize(data, serializer)
            writeText(content)
        }

        return@withContext true
    }

    override suspend operator fun <S : File.Schema> get(key: File.Key<S>): S = withContext(Dispatchers.IO) {
        val schema = _cache.computeIfAbsent(key) { k ->
            val path = _folder / k.file.path
            val serializer = k.file.serializer

            val formatter = k.file.format.formatter

            path.file.run { formatter.deserialize(this, serializer) }
        }

        schema as S
    }

    override suspend fun <S : File.Schema> default(key: File.Key<S>): Boolean = withContext(Dispatchers.IO) {
        val path = _folder / key.file.path
        val serializer = key.file.serializer
        val formatter = key.file.format.formatter

        path.file.run {
            if (exists) return@run false

            parentFile.mkdirs()

            _cache[key] = key.default

            val content = formatter.serialize(key.default, serializer)
            writeText(content)

            return@run true
        }
    }

    override fun clear() = _cache.clear()
}