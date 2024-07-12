plugins {
    id("android-app-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    defaultConfig {
        applicationId = "com.example.todoapp"
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders.put("YANDEX_CLIENT_ID", "7e6936cbf4094dd9b2385d66a3c98aaf")
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.feature.todo)
    implementation(projects.feature.auth)
    implementation(projects.core.designsystem)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.work.runtime.ktx)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.androidCompiler)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}