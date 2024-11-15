plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)


}

android {
    namespace = "com.anshuman.uniroomie"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.anshuman.uniroomie"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding{
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // For Animation
    implementation ("com.airbnb.android:lottie:6.6.0")
   // For Date View
    implementation("io.github.chaosleung:pinview:1.4.4")
    // For Fragement Navagation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // fire base DOM
    implementation ("com.google.firebase:firebase-bom:33.5.1")

    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.google.code.gson:gson:2.8.9")

    // Location Activity
    implementation ("com.google.android.gms:play-services-location:15.0.1")
    implementation ("com.google.android.gms:play-services-places:15.0.1")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("androidx.fragment:fragment:1.5.5")
    implementation ("com.android.support:support-compat:26.1.0")
    implementation ("androidx.core:core:1.13.1")

    // Picaso Dependecy
    implementation ("com.squareup.picasso:picasso:2.8")













}