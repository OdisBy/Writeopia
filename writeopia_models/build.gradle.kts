@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

rootProject.extra.apply {
    set("PUBLISH_GROUP_ID", "io.writeopia")
    set("PUBLISH_ARTIFACT_ID", "writeopia-models")
    set("PUBLISH_VERSION", libs.versions.writeopia.get())
}

apply(from = "${rootDir}/scripts/publish-module.gradle")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}