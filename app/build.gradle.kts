plugins {
    alias(libs.plugins.android.application)
    id("com.github.spotbugs") version "4.7.1"
}

android {
    namespace = "com.example.project8_3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.project8_3"
        minSdk = 24
        targetSdk = 35
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

    lint {
        abortOnError = false
        warningsAsErrors = false
        showAll =  true
    }
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    ignoreFailures.set(true)
}

tasks.withType<com.github.spotbugs.snom.SpotBugsTask> {
    reports {
        create("html") {
            isEnabled = true
        }
        create("xml") {
            isEnabled = false
        }
    }
}

dependencies {

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.work:work-runtime-ktx:2.10.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}