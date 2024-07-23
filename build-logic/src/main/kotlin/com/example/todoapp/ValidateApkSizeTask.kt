package com.example.todoapp

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class ValidateApkSizeTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val active: Property<Boolean?>

    @get:Input
    abstract val maxSizeInKb: Property<Long?>

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @get:OutputFile
    abstract val outputSizeInKbFile: RegularFileProperty

    @TaskAction
    fun validate() {

        val apkFile = apkDir.get().asFile.listFiles()
            ?.firstOrNull { it.name.endsWith(".apk") }
            ?: throw GradleException("APK file not found")

        val maxSizeInKb = maxSizeInKb.get() ?: (1024 * 128)
        val sizeInKb = apkFile.length() / 1024

        outputSizeInKbFile.get().asFile.writeText(sizeInKb.toString())

        if (active.get() == false) {
            println("Validation is disabled")
            return
        }

        if (sizeInKb > maxSizeInKb) {
            sendTelegramMessage("APK size is too large: ${sizeInKb}KB (max: ${maxSizeInKb}KB)")
            throw GradleException("APK size is too large: ${sizeInKb}KB (max: ${maxSizeInKb}KB)")
        } else {
            println("APK size is acceptable: ${sizeInKb}KB")
        }
    }

    private fun sendTelegramMessage(message: String) {
        val token = token.get()
        val chatId = chatId.get()
        runBlocking {
            telegramApi.sendMessage(message, token, chatId).apply {
                println("Status = $status")
            }
        }
    }
}