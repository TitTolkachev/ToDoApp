plugins {
    id("android-core-lib-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.todoapp.core.data"
}

dependencies {
    api(projects.core.model)
    api(projects.core.datastore)
    api(projects.core.network)
    api(projects.core.database)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.androidCompiler)
}