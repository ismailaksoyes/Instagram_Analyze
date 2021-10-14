plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = buildConfigVersions.compileSdkVersion
    buildToolsVersion = buildConfigVersions.buildToolsVersion
    defaultConfig {
        applicationId = "com.avalon.calizer"
        minSdk = buildConfigVersions.minSdkVersion
        targetSdk = buildConfigVersions.targetSdkVersion
        versionCode = buildConfigVersions.versionCode
        versionName = buildConfigVersions.versionName
        buildFeatures.dataBinding
        buildFeatures.viewBinding
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas".toString())
            }
        }
    }
    lint {
        isCheckDependencies = true
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion =  "1.0.1"
    }


}

dependencies {
    implementation(androidxsupportDependencies.core_ktx)
    implementation(androidxsupportDependencies.appcompat)
    implementation(androidxsupportDependencies.material)
    implementation(androidxsupportDependencies.preference)
    implementation(androidxsupportDependencies.constraintlayout)
    implementation(androidxsupportDependencies.multidex)
    implementation(androidxsupportDependencies.legacy_support)
    //Kotlin components
    implementation(kotlinDependencies.kotlin)
    implementation(kotlinDependencies.coroutines_core)
    implementation(kotlinDependencies.coroutines)
    //retrofit
    implementation(retrofitDependencies.retrofit2)
    implementation(retrofitDependencies.converter_gson)
    implementation(retrofitDependencies.adapter_rxjava)
    implementation(retrofitDependencies.logging_interceptor)
    // Room components
    implementation(roomDependencies.room_ktx)
    kapt(roomDependencies.room_compiler)
    testImplementation(roomDependencies.room_testing)
    //Lifecycle
    implementation(androidxsupportDependencies.lifecycle_viewmodel)
    implementation(androidxsupportDependencies.lifecycle_runtime)

    //Testing
    testImplementation(testingDepencies.junit)
    testImplementation(testingDepencies.junit_ext)
    testImplementation(testingDepencies.espresso)

    //glide
    implementation(glideDependencies.glide)
    kapt(glideDependencies.glide_compiler)


    implementation(mlkitDependencies.mlkit_face)
    implementation(mlkitDependencies.mlkit_pose)

    //Dagger - Hilt
    implementation(hiltDependencies.hilt)
    kapt(hiltDependencies.hilt_compiler)

    //Fb Shimmer Animation
    implementation(shimmerDependencies.shimmer)


    // Activity KTX for viewModels()
    implementation(androidxsupportDependencies.activity_ktx)

    //navigation comp
    implementation(androidxsupportDependencies.navigation_fragment)
    implementation(androidxsupportDependencies.navigation_ui)

    implementation(lottieDependencies.lottie)

    implementation(kotlinDependencies.coroutines_play_services)

    //compose
    implementation(composeDependencies.composeFoundation)
    implementation(composeDependencies.composeLiveData)
    implementation(composeDependencies.composeMaterial)
    implementation(composeDependencies.composeMaterialIconsCore)
    implementation(composeDependencies.composeMaterialIconsExtended)
    implementation(composeDependencies.composeRxJava)
    implementation(composeDependencies.composeUi)
    implementation(composeDependencies.composeUiToolkit)
    implementation(composeDependencies.composeUiToolkit)

    //coil
    implementation(coilDependencies.coil)


}