plugins {
    id("android-app-convention")
    id("telegram-reporter")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

apkSizeValidator {
    enabled.set(true)
    maxSizeInKb.set(100000)
}

telegramBuildReporter {
    token.set("")
    chatId.set("")
//    token.set(providers.environmentVariable("TG_TOKEN"))
//    chatId.set(providers.environmentVariable("TG_CHAT"))
}

android {
    namespace = "com.example.todoapp"
    defaultConfig {
        applicationId = "com.example.todoapp"
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["YANDEX_CLIENT_ID"] = "7e6936cbf4094dd9b2385d66a3c98aaf"
    }

    afterEvaluate {
        applicationVariants.all { variant ->
            variant.outputs.all { output ->
                val newApkName = "todolist-${variant.versionName}-${variant.versionCode}.apk"
                val renamedApkFile = File(output.outputFile.parent, newApkName)
                output.outputFile.renameTo(renamedApkFile)
            }
        }
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.feature.todo)
    implementation(projects.feature.auth)
    implementation(projects.feature.about)
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