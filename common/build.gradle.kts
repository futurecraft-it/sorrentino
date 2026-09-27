plugins {
    id("sorrentino.common-conventions")
}

dependencies {
    api(project(":api"))

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.atomicfu)
    api(libs.bundles.kotlinx.serialization)

    compileOnly(libs.ktor.client.core)
    compileOnly(libs.ktor.client.okhttp)
    compileOnly(libs.ktor.client.content.negotiation)
    compileOnly(libs.ktor.serialization)

    compileOnly(libs.ktor.server.core)
    compileOnly(libs.ktor.server.netty)
    compileOnly(libs.ktor.server.double.receive)
    compileOnly(libs.ktor.server.content.negotiation)

    compileOnlyApi(libs.exposed.core)
    compileOnlyApi(libs.exposed.jdbc)
    compileOnlyApi(libs.exposed.dao)
    compileOnlyApi(libs.exposed.datetime)

    implementation(libs.database.hikari)
    compileOnly(libs.database.sqlite)
    compileOnly(libs.database.mysql)
    compileOnly(libs.database.postgresql)
    compileOnly(libs.database.mariadb)
    compileOnly(libs.database.h2)

    compileOnlyApi(libs.twitch4j)
    compileOnlyApi(libs.events4j)
}
