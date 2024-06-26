plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.prm2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.prm2"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation("androidx.compose.material:material-icons-extended")
    implementation(platform(libs.androidx.compose.bom))
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.auth)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation ("com.google.maps.android:maps-compose:5.0.3")
    implementation ("com.google.maps.android:maps-compose-utils:5.0.3")
    // Optionally, you can include the widgets library for ScaleBar, etc.
    implementation ("com.google.maps.android:maps-compose-widgets:5.0.3")
    implementation ("io.coil-kt:coil-compose:2.4.0")
    implementation ("com.google.accompanist:accompanist-permissions:0.31.1-alpha")
    implementation(libs.play.services.location)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.firebase.crashlytics.buildtools)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}