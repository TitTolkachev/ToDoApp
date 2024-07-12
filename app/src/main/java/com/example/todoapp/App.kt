package com.example.todoapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.todoapp.sync.InternetConnectionListener
import com.example.todoapp.sync.TodoListSyncWorker.Companion.startTodoListSync
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Основное приложение.
 */
@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var internetConnectionListener: InternetConnectionListener

    override val workManagerConfiguration: Configuration by lazy {
        Configuration.Builder().setWorkerFactory(workerFactory).build()
    }

    override fun onCreate() {
        super.onCreate()

        // Синхронизация списка задач
        applicationContext.startTodoListSync()

        // Отслеживание статуса интернет соединения
        internetConnectionListener.startListener()
    }
}