package com.example.todoapp.di

import com.example.todoapp.data.service.InternetConnectionListener
import com.example.todoapp.data.service.TodoListSyncWorker
import com.example.todoapp.domain.repository.AuthRepository
import com.example.todoapp.domain.repository.TodoItemsRepository

/**
 * Буду с ручным DI, пока не изучу Dagger.
 * https://developer.android.com/training/dependency-injection/manual
 */
interface AppContainer {
    val todoItemsRepository: TodoItemsRepository
    val authRepository: AuthRepository
    val internetConnectionListener: InternetConnectionListener
}