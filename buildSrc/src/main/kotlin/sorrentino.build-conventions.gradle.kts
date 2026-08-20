import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("com.gradleup.shadow")
    id("xyz.jpenilla.run-paper")
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

tasks.jar {
//    manifest.attributes(
//        "paperweight-mappings-namespace" to "mojang",
//    )
}

tasks.shadowJar {
    archiveClassifier.set("")
    archiveBaseName.set("sorrentino")
    archiveVersion.set("${project.version}")

    mergeServiceFiles()
    filesMatching("META-INF/services/**") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    relocations()
    excludes()
}

fun ShadowJar.relocations() {}

fun ShadowJar.excludes() {}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}
