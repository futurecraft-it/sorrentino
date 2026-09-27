import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import kotlin.jvm.optionals.getOrNull

plugins {
    id("java-library")
    id("com.gradleup.shadow")
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

tasks.processResources {
    dependsOn(tasks.getByName("saveCatalogue"))
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

tasks.register("saveCatalogue") {
    description = "Saves the dependency catalogue to a json file."


    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    val resourceFile = layout.buildDirectory.file("resources/main/libraries.json")

    outputs.file(resourceFile)

    doLast {
        val file = resourceFile.get().asFile
        file.parentFile.mkdirs()

        val entries = libs.libraryAliases.mapNotNull { alias ->
            val library = libs.findLibrary(alias).getOrNull()?.get() ?: return@mapNotNull null

            """
                "$alias": {
                    "groupId": "${library.group}",
                    "artifactId": "${library.name}",
                    "version": "${library.versionConstraint.requiredVersion}"
                }
            """.trimIndent()
        }.joinToString(",\n")

        file.writeText("{\n$entries\n}")
    }
}