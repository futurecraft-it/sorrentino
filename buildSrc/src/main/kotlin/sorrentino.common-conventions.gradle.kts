plugins {
    java
    `java-library`
}

val libs = extensions.getByType(VersionCatalogsExtension::class.java)
    .named("libs")

group = "it.futurecraft.sorrentino"
version = libs.findVersion("project").get()

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://api.modrinth.com/maven")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.findLibrary("adventure").get())
    implementation(libs.findLibrary("guice").get())

    // Test
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    withSourcesJar()
}
