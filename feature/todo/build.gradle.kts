plugins {
    id("android-feature-lib-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.todoapp.feature.todo"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)
    api(projects.core.designsystem)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.androidCompiler)
}