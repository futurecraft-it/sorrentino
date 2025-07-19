import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow")
    id("sorrentino.common-conventions")
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")

    relocations()
    excludes()
}

fun ShadowJar.relocations() {}

fun ShadowJar.excludes() {}

tasks.named("build") {
    dependsOn("shadowJar")
}