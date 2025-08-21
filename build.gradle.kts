
group = "it.futurecraft.sorrentino"
version = "1.0-SNAPSHOT"

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
