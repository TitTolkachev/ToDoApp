package com.example.todoapp.data.service

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.todoapp.App
import java.util.concurrent.TimeUnit

/**
 * Выполняет синхронизацию  сервера и локального хранилища раз в 8 часов.
 */
class TodoListSyncWorker(
    appContext: Context, workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val todoItemsRepository = (applicationContext as App).container.todoItemsRepository
        try {
            todoItemsRepository.sync()
            return Result.success()
        } catch (_: Exception) {
            return Result.retry()
        }
    }

    companion object {
        fun Context.startTodoListSync() {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<TodoListSyncWorker>(8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                "TodoListSyncWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }
    }
}