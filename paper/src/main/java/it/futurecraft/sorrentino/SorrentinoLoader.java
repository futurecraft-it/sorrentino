package it.futurecraft.sorrentino;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

@SuppressWarnings("UnstableApiUsage")
public class SorrentinoLoader implements PluginLoader {
    @Override
    public void classloader(PluginClasspathBuilder builder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        resolver.addRepository(
                new RemoteRepository.Builder(
                        "central",
                        "default",
                        MavenLibraryResolver.MAVEN_CENTRAL_DEFAULT_MIRROR
                ).build()
        );

        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlin:kotlin-stdlib:2.2.10"), null));;
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.10"), null));;
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlin:kotlin-reflect:2.2.10"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlinx:atomicfu:0.29.0"), null));

        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.8.0"), null));

        resolver.addDependency(new Dependency(new DefaultArtifact("io.ktor:ktor-client-core:3.2.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.ktor:ktor-client-cio:3.2.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.ktor:ktor-client-content-negotiation:3.2.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.ktor:ktor-server-core:3.2.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.ktor:ktor-server-netty:3.2.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.ktor:ktor-server-double-receive:3.2.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.ktor:ktor-server-content-negotiation:3.2.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.ktor:ktor-serialization-kotlinx-json:3.2.3"), null));

        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.exposed:exposed-core:1.0.0-beta-5"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.exposed:exposed-dao:1.0.0-beta-5"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.exposed:exposed-jdbc:1.0.0-beta-5"), null));

        resolver.addDependency(new Dependency(new DefaultArtifact("io.insert-koin:koin-core:4.1.0"), null));

        builder.addLibrary(resolver);
    }
}
