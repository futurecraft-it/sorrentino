import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-library`

    kotlin("jvm")
    kotlin("plugin.serialization")
}

val libs = extensions.getByType(VersionCatalogsExtension::class.java)
    .named("libs")

group = "it.futurecraft.sorrentino"
version = libs.findVersion("project").get()

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://api.modrinth.com/maven")
    maven("https://repo.papermc.io/repository/maven-public/")
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }

    withSourcesJar()
}

kotlin {
    jvmToolchain(25)

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_25)

        optIn.addAll(
            "kotlin.io.path.ExperimentalPathApi",
            "kotlin.time.ExperimentalTime",
            "kotlin.experimental.ExperimentalTypeInference"
        )
    }
}

