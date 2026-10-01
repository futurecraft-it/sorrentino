plugins {
    id("sorrentino.common-conventions")
    id("sorrentino.build-conventions")

    id("org.jetbrains.kotlin.kapt")
}

dependencies {
    implementation(libs.velocity.api)
    kapt(libs.velocity.api)
}