package it.futurecraft.sorrentino

import io.papermc.paper.plugin.loader.PluginClasspathBuilder
import io.papermc.paper.plugin.loader.PluginLoader
import it.futurecraft.sorrentino.utils.Dependencies
import xyz.jpenilla.gremlin.runtime.platformsupport.PaperClasspathAppender

@Suppress("UnstableApiUsage", "Unused")
class SorrentinoLoader : PluginLoader {
    override fun classloader(classpathBuilder: PluginClasspathBuilder) {
        val out = classpathBuilder.context.dataDirectory.resolve("libs")
        PaperClasspathAppender(classpathBuilder).append(Dependencies.resolve(out))
    }
}