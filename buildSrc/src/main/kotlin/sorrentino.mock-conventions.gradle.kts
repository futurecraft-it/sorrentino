import xyz.jpenilla.runtask.task.AbstractRun

plugins {
    java
    id("xyz.jpenilla.run-paper")
}

tasks {
    runServer {
        downloadPlugins { modrinth("placeholderapi", "2.11.6") }

        runDirectory.set(
            layout.projectDirectory.dir("server")
        )

        jvmArgs("-Xmx2G", "-Xms2G")
        minecraftVersion("1.21.8")
    }
}

tasks.withType(AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }

    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}