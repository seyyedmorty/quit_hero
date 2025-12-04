plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.quithero"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.quithero"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10" // هماهنگ با Compose BOM
    }
}

dependencies {
    // -----------------------------
    // Core / Kotlin / Coroutines
    // -----------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    // -----------------------------
    // Compose BOM
    // -----------------------------
    implementation(platform(libs.androidx.compose.bom))

    // -----------------------------
    // Compose UI / Foundation / Material
    // -----------------------------
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)

    // -----------------------------
    // Navigation / ViewModel / Lifecycle
    // -----------------------------
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // -----------------------------
    // Room Database
    // -----------------------------
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // -----------------------------
    // WorkManager (Notifications / Background)
    // -----------------------------
    implementation(libs.androidx.work.runtime.ktx)

    // -----------------------------
    // Network (Retrofit + Gson)
    // -----------------------------
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // -----------------------------
    // DataStore
    // -----------------------------
    implementation(libs.androidx.datastore.preferences)

    // -----------------------------
    // Testing
    // -----------------------------
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // -----------------------------
    // Debug Tools
    // -----------------------------
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
