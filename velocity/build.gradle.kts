plugins {
    id("sorrentino.common-conventions")
    id("sorrentino.build-conventions")
}

dependencies {
    implementation(project(":common"))

    implementation(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
}