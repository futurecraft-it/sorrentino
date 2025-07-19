plugins {
    id("sorrentino.build-conventions")
    id("sorrentino.common-conventions")
}

dependencies {
    api(project(":api"))

    implementation(libs.bundles.ktor)
    implementation(libs.bstats)
    implementation("io.ktor:ktor-server-content-negotiation:3.0.3")
    implementation("io.ktor:ktor-serialization-gson:3.0.3")
    implementation("io.ktor:ktor-server-content-negotiation:3.0.3")
    implementation("io.ktor:ktor-serialization-jackson:3.0.3")
}