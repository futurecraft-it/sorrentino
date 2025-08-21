import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-library`

    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "it.futurecraft.sorrentino"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://api.modrinth.com/maven")
    maven("https://repo.papermc.io/repository/maven-public/")
}

val libs = extensions.getByType(VersionCatalogsExtension::class.java)
    .named("libs")

dependencies {
    implementation(libs.findBundle("kotlin").get())
    implementation(libs.findBundle("exposed").get())
    implementation(libs.findBundle("ktor.client").get())
    implementation(libs.findBundle("serialization").get())

    implementation(libs.findLibrary("koin.core").get())

    compileOnly(libs.findLibrary("paper").get())
    compileOnly(libs.findLibrary("adventure").get())
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    withSourcesJar()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)

        optIn.addAll(
            "kotlin.io.path.ExperimentalPathApi",
            "kotlin.time.ExperimentalTime",
            "kotlin.experimental.ExperimentalTypeInference"
        )
    }
}
