package com.example.todoapp

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class TelegramReporterTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:InputFile
    abstract val apkSizeKbFile: RegularFileProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun report() {
        val token = token.get()
        val chatId = chatId.get()
        val apkSizeKb = apkSizeKbFile.get().asFile.readText().toIntOrNull() ?: -1
        val files = apkDir.get().asFile.listFiles()
        val apkFile = files?.firstOrNull { it.name.endsWith(".apk") }
            ?: throw GradleException("APK file not found")

        runBlocking {
            telegramApi.sendMessage(
                "Build finished.\nAPK: ${apkFile.name}, Size: ${apkSizeKb / 1024}MB",
                token,
                chatId
            ).apply {
                println("Status = $status")
            }
        }
        runBlocking {
            telegramApi.sendFile(apkFile, token, chatId).apply {
                println("Status = $status")
            }
        }

    }
}