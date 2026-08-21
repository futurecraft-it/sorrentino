plugins {
    id("sorrentino.common-conventions")
}

dependencies {
    api(project(":api"))

    implementation(libs.javalin)

    implementation(libs.twitch4j)
    implementation(libs.events4j)

    implementation(libs.hikari)

    annotationProcessor(libs.jimmer.annotation)

    runtimeOnly(libs.mysql)
    runtimeOnly(libs.sqlite)
    runtimeOnly(libs.mariadb)
    runtimeOnly(libs.postgresql)

    implementation(libs.okhttp)
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
}