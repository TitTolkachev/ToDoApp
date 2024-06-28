package com.example.todoapp.di

import com.example.todoapp.data.repository.TodoItemsRepository

/**
 * Буду с ручным DI, пока не изучу Dagger.
 * https://developer.android.com/training/dependency-injection/manual
 */
interface AppContainer {
    val todoItemsRepository: TodoItemsRepository
}