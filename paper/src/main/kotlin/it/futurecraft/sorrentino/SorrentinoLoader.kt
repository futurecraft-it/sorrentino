package it.futurecraft.sorrentino

import io.papermc.paper.plugin.loader.PluginClasspathBuilder
import io.papermc.paper.plugin.loader.PluginLoader
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver
import kotlinx.serialization.json.Json
import org.eclipse.aether.artifact.DefaultArtifact
import org.eclipse.aether.graph.Dependency
import org.eclipse.aether.repository.RemoteRepository

class SorrentinoLoader : PluginLoader {
    val LIBRARIES = listOf(
        // Ktor Client
        "ktor-client-core",
        "ktor-client-okhttp",
        "ktor-client-content-negotiation",
        "ktor-serialization",

        // Ktor Server
        "ktor-server-core",
        "ktor-server-netty",
        "ktor-server-double-receive",
        "ktor-server-content-negotiation",

        // Exposed
        "exposed-core",
        "exposed-jdbc",
        "exposed-dao",
        "exposed-datetime",

        // Database
        "database-sqlite",
        "database-mysql",
        "database-postgresql",
        "database-mariadb",
        "database-h2",

        // Twitch4J
        "twitch4j",
        "events4j"
    )

    val catalogue: Catalogue by lazy {
        val stream = javaClass.classLoader.getResourceAsStream("libraries.json") ?:
            error("Could not find the libraries.json file")

        val body = stream.bufferedReader().readText()
        Json.decodeFromString<Catalogue>(body)
    }

    override fun classloader(classpathBuilder: PluginClasspathBuilder) {
        val resolver = MavenLibraryResolver()

        val mavenCentral = RemoteRepository.Builder("central", "default", "https://repo1.maven.org/maven2/")
            .build()

        val jitpack = RemoteRepository.Builder("jitpack", "default", "https://jitpack.io/")
            .build()

        val modrinth = RemoteRepository.Builder("modrinth", "default", "https://api.modrinth.com/maven/")
            .build()

        resolver.addRepository(mavenCentral)
        resolver.addRepository(jitpack)
        resolver.addRepository(modrinth)

        LIBRARIES.forEach {
            val item: CatalogueItem = catalogue[it] ?:
                error("Dependency $it not found in catalogue")

            resolver.addDependency(Dependency(DefaultArtifact("$item"), null))
        }

        classpathBuilder.addLibrary(resolver)
    }
}