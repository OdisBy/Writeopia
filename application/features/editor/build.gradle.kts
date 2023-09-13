plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    id("com.google.devtools.ksp")
}

android {
    namespace = "io.writeopia.editor"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

kotlin{
    sourceSets.all {
        languageSettings {
            languageVersion = "1.9"
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(project(":writeopia"))
    implementation(project(":writeopia_models"))
    implementation(project(":writeopia_persistence"))
    implementation(project(":writeopia_serialization"))

    implementation(project(":application:resources"))
    implementation(project(":application:utils"))
    implementation(project(":application:auth_core"))
    implementation(project(":application:common_ui"))

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.appCompat)
    implementation(libs.material)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.viewmodel.compose)
    implementation(libs.runtime.compose)
    implementation(libs.navigation.compose)

    implementation("androidx.activity:activity-compose")
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.accompanist.systemuicontroller)

    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class")

    // Compose - Preview
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation(platform(libs.androidx.compose.bom))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}