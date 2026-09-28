import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.accessors.dm.LibrariesForLibs

val Project.libs: LibrariesForLibs
    get() = the()