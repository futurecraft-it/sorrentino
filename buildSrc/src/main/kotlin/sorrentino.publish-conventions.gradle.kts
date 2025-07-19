plugins {
    signing
    `maven-publish`

    // id("org.jetbrains.dokka")
}

/* dependencies {
    dokkaHtmlPlugin("org.jetbrains.dokka:mathjax-plugin:1.9.20")
    dokkaHtmlPlugin("org.jetbrains.dokka:versioning-plugin:1.9.20")
}

tasks.dokkaHtml {

} */

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                // TODO: Add pom configuration here
            }
        }
    }
}
