plugins {
    id(KotlinPlugins.mainAndroidAppPlugin)
    kotlin("android")
    kotlin("kapt")
    id(KotlinPlugins.navArgs)
    id(KotlinPlugins.hiltPlugin)
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId =  Android.appId
        minSdk = Android.minSdk
        targetSdk =  Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures{
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    // Main libraries
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Google.material)
    implementation(Google.googleGson)
    implementation(Kotlinx.kotlinReflect)
    implementation(OtherLibs.multiDex)
    implementation(AndroidX.liveData)
    implementation(AndroidX.viewModel)

    // Coroutines Integration
    implementation(Kotlinx.coroutinesCore)
    implementation(Kotlinx.coroutinesAndroid)

    // Hilt integration
    implementation(Hilt.android)
    kapt(Hilt.compiler)

    // Timber for Logging Integration
    implementation(Jakewharton.timber)

    // Android Navigation Integration
    implementation(Navigation.fragmentKtx)
    implementation(Navigation.navFragment)
    implementation(Navigation.navUI)

    // Room Integration
    implementation(Room.runtime)
    kapt(Room.kapt)
    implementation(Room.ktx)

    // Retrofit & OKHTTP Integration
    implementation(Retrofit.retrofitCore)
    implementation(Retrofit.retrofitConverter)
    implementation(Retrofit.retrofitConverterScalars)
    implementation(Retrofit.okHTTP)
    implementation(Retrofit.okHTTPInterceptor)

    // Add Location Service GMS
    implementation(GmsServices.locationService)



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}