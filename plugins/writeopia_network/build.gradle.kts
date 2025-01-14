@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlinSerialization)
    kotlin("multiplatform")
}


//rootProject.extra.apply {
//    set("PUBLISH_GROUP_ID", "io.writeopia")
//    set("PUBLISH_ARTIFACT_ID", "writeopia-network")
//    set("PUBLISH_VERSION", libs.versions.writeopia.get())
//}

//apply(from = "${rootDir}/scripts/publish-module.gradle")

kotlin {
    jvm {}

    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":writeopia_models"))
                implementation(project(":plugins:writeopia_serialization"))

                implementation(libs.kotlinx.serialization.json)

                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.serialization.json)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.client.logging)

                implementation(libs.kotlinx.serialization.json)
            }
        }

        val jvmMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlinx.coroutines.android)
            }
        }
    }
}

