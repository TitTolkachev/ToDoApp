package com.example.todoapp.practice

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create

class TelegramReporterPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("Android not found")

        val extension = project.extensions.create("telegramBuildReporter", TelegramExtension::class)
        val telegramApi = TelegramApi(HttpClient(OkHttp))
        androidComponents.onVariants { variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            project.tasks.register(
                "reportTelegramApkFor${variant.name.capitalize()}",
                TelegramReporterTask::class.java,
                telegramApi
            ).configure {
                apkDir.set(artifacts)
                token.set(extension.token)
                chatId.set(extension.chatId)
            }
        }
    }

    private fun String.capitalize(): String {
        return replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase()
            } else {
                it.toString()
            }
        }
    }
}

interface TelegramExtension {
    val chatId: Property<String>
    val token: Property<String>
}
