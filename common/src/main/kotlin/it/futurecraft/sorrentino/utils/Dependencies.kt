package it.futurecraft.sorrentino.utils

import org.slf4j.LoggerFactory
import xyz.jpenilla.gremlin.runtime.DependencyCache
import xyz.jpenilla.gremlin.runtime.DependencyResolver
import xyz.jpenilla.gremlin.runtime.DependencySet
import xyz.jpenilla.gremlin.runtime.logging.Slf4jGremlinLogger
import java.nio.file.Path

object Dependencies {
    fun resolve(out: Path): Set<Path> {
        val dependencies: DependencySet =
            DependencySet.readFromClasspathResource(javaClass.classLoader, "sorrentino-dependencies.txt")
        val cache = DependencyCache(out)

        val logger = LoggerFactory.getLogger(javaClass)

        try {
            return DependencyResolver(Slf4jGremlinLogger(logger)).use { downloader ->
                downloader.resolve(dependencies, cache)
                    .jarFiles()
            }
        } finally {
            cache.cleanup()
        }

    }
}