plugins {
    id("convention.android.feature")
    id("convention.android.library.compose")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.android.feature.calendar"
}

dependencies {
//    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:network"))
    implementation(libs.kotlinx.datetime)

}