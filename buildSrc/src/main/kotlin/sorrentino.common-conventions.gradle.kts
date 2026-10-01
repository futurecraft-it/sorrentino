import gradle.kotlin.dsl.accessors._8cede302156b6ff0aaf0c2baecf4f41d.implementation
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-library`

    kotlin("jvm")
    kotlin("plugin.serialization")
}


group = "it.futurecraft.sorrentino"
version = libs.versions.project.get()

print(version)

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://api.modrinth.com/maven")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.adventure)
    implementation(libs.gremlin.runtime)
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

