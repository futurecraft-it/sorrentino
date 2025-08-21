plugins {
    id("sorrentino.mock-conventions")
    id("sorrentino.build-conventions")
    id("sorrentino.common-conventions")
}

dependencies {
    api(project(":api"))

    implementation(libs.bundles.ktor.server)

    implementation(libs.bstats)
}
