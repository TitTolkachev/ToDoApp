package com.example.todoapp

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.utils.named

class TelegramReporterPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("Android not found")

        val reportExtension =
            project.extensions.create("telegramBuildReporter", TelegramExtension::class)
        val validateExtension =
            project.extensions.create("apkSizeValidator", SizeValidationExtension::class)

        val telegramApi = TelegramApi(HttpClient(OkHttp))

        androidComponents.onVariants { variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)

            val validateTask = project.tasks.register(
                "validateApkSizeFor${variant.name.capitalize()}",
                ValidateApkSizeTask::class.java,
                telegramApi
            )
            project.tasks.named<ValidateApkSizeTask>("validateApkSizeFor${variant.name.capitalize()}")
                .configure {
                    apkDir.set(artifacts)
                    active.set(validateExtension.enabled)
                    chatId.set(reportExtension.chatId)
                    token.set(reportExtension.token)
                    maxSizeInKb.set(validateExtension.maxSizeInKb)
                    outputSizeInKbFile.set(project.layout.buildDirectory.file("apk-file-size-kb.txt"))
                }

            project.tasks.register(
                "reportTelegramApkFor${variant.name.capitalize()}",
                TelegramReporterTask::class.java,
                telegramApi
            ).configure {
                apkDir.set(artifacts)
                token.set(reportExtension.token)
                chatId.set(reportExtension.chatId)
                apkSizeKbFile.set(validateTask.get().outputSizeInKbFile)
                dependsOn(validateTask)
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

interface SizeValidationExtension {
    val enabled: Property<Boolean?>
    val maxSizeInKb: Property<Long?>
}

