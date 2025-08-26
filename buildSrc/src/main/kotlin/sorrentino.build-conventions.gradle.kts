import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow")
    id("sorrentino.common-conventions")
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    archiveBaseName.set("sorrentino")
    archiveVersion.set("${project.version}")

    relocations()
    excludes()
}

fun ShadowJar.relocations() {
    relocate("com.fasterxml", "it.futurecraft.sorrentino.libs.fasterxml")
    relocate("com.github", "it.futurecraft.sorrentino.libs.github")
    relocate("org.bstats", "it.futurecraft.sorrentino.libs.bstats")
    relocate("org.koin", "it.futurecraft.sorrentino.libs.koin")
}

fun ShadowJar.excludes() {}

tasks.named("build") {
    dependsOn("shadowJar")
}