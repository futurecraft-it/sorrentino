import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("com.gradleup.shadow")
    id("xyz.jpenilla.gremlin-gradle")
}

dependencies {
    implementation(project(":sorrentino-common"))

    runtimeDownload(libs.bundles.ktor.client){
        isTransitive = false
    }
    runtimeDownload(libs.bundles.ktor.server){
        isTransitive = false
    }
    runtimeDownload(libs.bundles.exposed)
    runtimeDownload(libs.bundles.database.drivers) {
        isTransitive = false
    }
    runtimeDownload(libs.bundles.twitch4j)
}

configurations.runtimeDownload {
    exclude("org.slf4j", "slf4j-api")
}

tasks.writeDependencies {
    outputFileName = "sorrentino-dependencies.txt"

    repos.add("https://jitpack.io")
    repos.add("https://api.modrinth.com/maven")
    repos.add("https://repo.maven.apache.org/maven2/")
    repos.add("https://repo.papermc.io/repository/maven-public/")
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

tasks.shadowJar {
    archiveClassifier.set("")
    archiveBaseName.set("sorrentino")
    archiveVersion.set("${project.version}")

    mergeServiceFiles()
    // Needed for mergeServiceFiles to work properly in Shadow 9+
    filesMatching("META-INF/services/**") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    minimize {  }

    relocations()
    excludes()
}

fun ShadowJar.relocations() {
}

fun ShadowJar.excludes() {
    exclude("META-INF/maven/**")
    exclude("META-INF/*.RSA")
    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
    exclude("META-INF/DEPENDENCIES")
    exclude("META-INF/LICENSE*")
    exclude("META-INF/NOTICE*")

    exclude("**/*.kotlin_metadata")
    exclude("**/*.kotlin_module")
    exclude("**/*.kotlin_builtins")
}