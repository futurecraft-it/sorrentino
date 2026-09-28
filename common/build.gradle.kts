plugins {
    id("sorrentino.common-conventions")
}

dependencies {
    api(project(":api"))

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.atomicfu)
    api(libs.bundles.kotlinx.serialization)

    compileOnly(libs.bundles.ktor.client)
    compileOnly(libs.bundles.ktor.server)

    compileOnlyApi(libs.bundles.exposed)

    implementation(libs.database.hikari)
    compileOnly(libs.bundles.database.drivers)

    compileOnlyApi(libs.bundles.twitch4j)
}
