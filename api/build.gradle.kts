plugins {
    id("sorrentino.common-conventions")
    id("sorrentino.publish-conventions")
}

dependencies {
    api(libs.twitch4j)
    api(libs.events4j)
    implementation("io.ktor:ktor-client-encoding:3.2.3")
}
