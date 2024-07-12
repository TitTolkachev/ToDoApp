plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

//gradlePlugin {
//    plugins.register("myPlugin") {
//        id = "my-plugin"
//        implementationClass = "com.example.todoapp.plugins.MyPlugin"
//    }
//    plugins.register("telegram-reporter") {
//        id = "telegram-reporter"
//        implementationClass = "com.example.todoapp.practice.TelegramReporterPlugin"
//    }
//}

dependencies {
    implementation(libs.agp)
    implementation(libs.kotlin.gradle.plugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}