plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = ProjectConfig.appId
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    packaging {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/ASM")
    }
}

dependencies {
    implementation("com.google.android.play:app-update-ktx:2.1.0")
    
    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.material)
    implementation(Compose.runtime)
    implementation(Compose.navigation)
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.onboardingPresentation))
    implementation(project(Modules.onboardingDomain))
    implementation(project(Modules.trackerPresentation))
    implementation(project(Modules.trackerDomain))
    implementation(project(Modules.trackerData))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.dataStorePreferences)

    implementation(Coil.coilCompose)

    implementation(Google.material)

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.moshiConverter)

    kapt(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)

    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    testImplementation(Testing.truth)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.turbine)
    testImplementation(Testing.composeUiTest)
    testImplementation(Testing.mockk)
    testImplementation(Testing.mockWebServer)

    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.composeUiTest)
    androidTestImplementation(Testing.mockkAndroid)
    androidTestImplementation(Testing.mockWebServer)
    androidTestImplementation(Testing.hiltTesting)
    kaptAndroidTest(DaggerHilt.hiltCompiler)
    androidTestImplementation(Testing.testRunner)
}