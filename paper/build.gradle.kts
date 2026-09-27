plugins {
    id("sorrentino.common-conventions")
    id("sorrentino.build-conventions")
}

dependencies {
    implementation(project(":common"))

    compileOnly(libs.paper.api)
}