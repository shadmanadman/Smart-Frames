import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("com.google.dagger.hilt.android")
    id ("kotlin-kapt")
}

android {
    namespace = "com.sam.adams.taskmanagerwidget"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sam.adams.taskmanagerwidget"
        minSdk = 24
        targetSdk = 34
        versionCode = 6
        versionName = "1.6"

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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =  "1.5.3"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.glance:glance-appwidget:1.0.0")
    debugImplementation("com.google.android.glance.tools:appwidget-viewer:0.2.2")

    val lifecycleVersion = "2.6.2"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    val composeUiVersion = "1.5.1"
    implementation ("androidx.compose.ui:ui:$composeUiVersion")
    implementation ("androidx.compose.runtime:runtime-livedata:$composeUiVersion")
    implementation ("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    implementation ("androidx.compose.material:material:$composeUiVersion")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$composeUiVersion")
    debugImplementation ("androidx.compose.ui:ui-tooling:$composeUiVersion")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:$composeUiVersion")

    val hiltVersion = "2.48"
    implementation( "com.google.dagger:hilt-android:$hiltVersion")
    kapt ("com.google.dagger:hilt-compiler:$hiltVersion")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    val workerVersion = "2.8.1"
    implementation("androidx.work:work-runtime-ktx:$workerVersion")

    val hiltWorkerVersion = "1.0.0"
    implementation("androidx.hilt:hilt-work:$hiltWorkerVersion")

    val datastoreVersion = "1.1.0-alpha05"
    implementation("androidx.datastore:datastore-preferences:$datastoreVersion")

    val paperVersion = "2.7.2"
    implementation ("io.github.pilgr:paperdb:$paperVersion")

    val retrofitVersion = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    val okhttp3Version = "5.0.0-alpha.5"
    implementation ("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")
    implementation ("com.squareup.okhttp3:okhttp:$okhttp3Version")

    val coroutinesVersion = "1.7.3"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}