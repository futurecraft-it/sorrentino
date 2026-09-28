plugins {
    id("sorrentino.common-conventions")
}

dependencies {
    api(libs.kotlin.stdlib)
    api(libs.kotlinx.datetime)
    api(libs.kotlinx.coroutines)
    api(libs.kotlinx.serialization.core)
}
