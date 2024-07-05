package com.example.todoapp.di

import com.example.todoapp.data.repository.AuthRepository
import com.example.todoapp.data.repository.TodoItemsRepository
import com.yandex.authsdk.YandexAuthSdk

/**
 * Буду с ручным DI, пока не изучу Dagger.
 * https://developer.android.com/training/dependency-injection/manual
 */
interface AppContainer {
    val todoItemsRepository: TodoItemsRepository
    val yandexAuthSdk: YandexAuthSdk
    val authRepository: AuthRepository
}