plugins {
//    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

//dokka {
//    moduleName = "sorrentino"
//
//    dokkaPublications.html {
//        suppressInheritedMembers.set(true)
//        failOnWarning.set(true)
//        outputDirectory.set(layout.buildDirectory.dir("docs"))
//    }
//
//    pluginsConfiguration.html {
//        footerMessage.set("FUTURECRAFT (c) 2025")
//        customStyleSheets.from("assets/logo-styles.css")
//        customAssets.from("assets/logo-icon.png", "assets/logo-icon.svg")
//    }
//}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "sorrentino-api", version.toString())

    pom {
        name = "Sorrentino API"
        description = "API for Sorrentino Plugin."
        inceptionYear = "2025"
        url = "https://github.com/futurecraft-it/sorrentino/"
        licenses {
            license {
                name = "GNU Affero General Public License v3.0"
                url = "https://www.gnu.org/licenses/agpl-3.0.txt"
                distribution = "https://www.gnu.org/licenses/agpl-3.0.txt"
            }
        }
        developers {
            developer {
                id = "danieleguglietti"
                name = "Daniele Guglietti"
                url = "https://github.com/danieleguglietti/"
            }
        }
        scm {
            url = "https://github.com/futurecraft-it/sorrentino/"
            connection = "scm:git:git://github.com/futurecraft-it/sorrentino/.git"
            developerConnection = "scm:git:ssh://git@github.com/futurecraft-it/sorrentino/.git"
        }
    }
}