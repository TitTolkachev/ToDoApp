plugins {
    id("android-feature-lib-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.todoapp.feature.about"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(projects.core.data)
    api(projects.core.designsystem)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.androidCompiler)

    // DivKit
    implementation(libs.divkit.div)
    implementation(libs.divkit.core)
    implementation(libs.divkit.json)
    implementation(libs.divkit.picasso)
}