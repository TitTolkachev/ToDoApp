package com.example.todoapp

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.todoapp.data.service.TodoListSyncWorker.Companion.startTodoListSync
import com.example.todoapp.di.AppContainer
import com.example.todoapp.di.DefaultAppContainer

/**
 * Основное приложение.
 */
class App : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)

        // Синхронизация списка задач
        applicationContext.startTodoListSync()

        // Отслеживание статуса интернет соединения
        container.internetConnectionListener.startListener()
    }
}