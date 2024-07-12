plugins {
    id("android-core-lib-convention")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.todoapp.core.network"
}

dependencies {
    api(projects.core.datastore)
    api(projects.core.model)

    // Json Serialization
    implementation(libs.kotlinx.serialization.json)

    // OkHttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)

    // Retrofit
    api(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.androidCompiler)
}