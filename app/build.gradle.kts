plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Added for Firebase integration
}

android {
    namespace = "com.example.soundsight"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.soundsight"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {
    // Core Dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Networking and JSON Parsing
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.google.code.gson:gson:2.10")

    // Firebase Authentication
    implementation ("com.google.firebase:firebase-auth:22.0.1")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.litert)
    implementation(libs.litert.support.api)

    // Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //implementation("org.tensorflow:tensorflow-lite-api:2.16.1")
    implementation("androidx.camera:camera-camera2:1.0.0-beta05") // CameraX library
    implementation("androidx.camera:camera-lifecycle:1.0.0-beta05")
    implementation("androidx.camera:camera-view:1.0.0-alpha08") // Optional for camera preview
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")

    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation ("androidx.core:core:1.6.0")

}
