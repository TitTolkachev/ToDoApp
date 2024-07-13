plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins.register("telegram-reporter") {
        id = "telegram-reporter"
        implementationClass = "com.example.todoapp.TelegramReporterPlugin"
    }
}

dependencies {
    implementation(libs.agp)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.ktor.client)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.kotlinx.coroutines)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}