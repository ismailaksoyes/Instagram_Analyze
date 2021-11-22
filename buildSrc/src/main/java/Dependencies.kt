object Versions {
    const val constraint_layout_version = "2.1.0"
    const val lifecycle_version = "2.3.1"
    const val coroutines_version = "1.5.2"
    const val kotlin_version = "1.5.21"
    const val hilt_version = "2.38.1"
    const val room_version = "2.3.0"

    const val appcompat_version = "1.3.1"
    const val material_design_version = "1.4.0"

    const val core_ktx_version = "1.6.0"
    const val preference_ktx_version = "1.1.1"
    const val multidex_version = "1.0.3"
    const val legacy_support_version = "1.0.0"

    const val retrofit2_version = "2.9.0"
    const val okhttp_logging_interceptor_version = "4.9.0"

    const val junit_version = "4.13.2"
    const val ext_junit_version = "1.1.3"
    const val espresso_version = "3.4.0"

    const val glide_version = "4.12.0"

    const val mlkit_face_version = "16.1.2"
    const val mlkit_pose_version = "17.0.1-beta4"

    const val facebook_shimmer_version = "0.5.0"

    const val activity_ktx_version = "1.3.1"

    const val navigation_comp_version = "2.4.0-alpha01"

    const val airbnb_lottie_version = "3.4.0"

    const val coroutines_play_services_version = "1.2.1"

    const val android_gradle_plugin_version = "7.0.2"
    const val gms_play_services_version = "4.3.8"

    const val compose_version = "1.0.1"

    const val coil_version = "1.4.0"

    const val fragment_ktx_version = "1.4.0"

}
object buildConfigVersions{
    const val compileSdkVersion = 31
    const val buildToolsVersion = "30.0.2"
    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0"
}

object BuildPluginsDependencies{
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin_version}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
    const val gmsPlayServicesPlugin = "com.google.gms:google-services:${Versions.gms_play_services_version}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}"
    const val navigationGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation_comp_version}"
}

object androidxsupportDependencies {
    const val legacy_support = "androidx.legacy:legacy-support-v4:${Versions.legacy_support_version}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout_version}"
    const val preference = "androidx.preference:preference-ktx:${Versions.preference_ktx_version}"
    const val lifecycle_viewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_version}"
    const val activity_ktx = "androidx.activity:activity-ktx:${Versions.activity_ktx_version}"
    const val material = "com.google.android.material:material:${Versions.material_design_version}"
    const val navigation_fragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_comp_version}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation_comp_version}"
    const val navigation_runtime = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation_comp_version}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx_version}"
    const val multidex = "com.android.support:multidex:${Versions.multidex_version}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx_version}"
}

object kotlinDependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines_version}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"


}

object retrofitDependencies {
    const  val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2_version}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2_version}"
    const val adapter_rxjava = "com.squareup.retrofit2:adapter-rxjava:${Versions.retrofit2_version}"
    const val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor_version}"
}

object roomDependencies {
    const val room_ktx = "androidx.room:room-ktx:${Versions.room_version}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room_version}"
    const val room_testing = "androidx.room:room-testing:${Versions.room_version}"
}

object testingDepencies {
    const val junit = "junit:junit:${Versions.junit_version}"
    const val junit_ext = "androidx.test.ext:junit:${Versions.ext_junit_version}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso_version}"
}

object glideDependencies {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"
}

object coilDependencies{
    const val coil = "io.coil-kt:coil-compose:${Versions.coil_version}"
}

object mlkitDependencies {
    const val mlkit_face = "com.google.mlkit:face-detection:${Versions.mlkit_face_version}"
    const val mlkit_pose = "com.google.mlkit:pose-detection:${Versions.mlkit_pose_version}"
}

object hiltDependencies {
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt_version}"
    const val hilt_compiler = "com.google.dagger:hilt-compiler:${Versions.hilt_version}"
}

object shimmerDependencies {
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.facebook_shimmer_version}"
}

object lottieDependencies {
    const val lottie = "com.airbnb.android:lottie:${Versions.airbnb_lottie_version}"
}

object composeDependencies{
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose_version}"
    const val composeUiToolkit = "androidx.compose.ui:ui-tooling:${Versions.compose_version}"
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose_version}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose_version}"
    const val composeMaterialIconsCore = "androidx.compose.material:material-icons-core:${Versions.compose_version}"
    const val composeMaterialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose_version}"
    const val composeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose_version}"
    const val composeRxJava = "androidx.compose.runtime:runtime-rxjava2:${Versions.compose_version}"

}


