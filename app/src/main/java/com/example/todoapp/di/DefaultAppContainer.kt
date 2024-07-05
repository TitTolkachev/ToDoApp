package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.data.local.PrefsDataStore
import com.example.todoapp.data.repository.AuthRepository
import com.example.todoapp.data.repository.TodoItemsRepository
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk

/**
 * Буду с ручным DI, пока не изучу Dagger.
 * https://developer.android.com/training/dependency-injection/manual
 */
class DefaultAppContainer(private val appContext: Context) : AppContainer {

    private val _prefsDataStore = PrefsDataStore(appContext)

    private val network = Network(_prefsDataStore)

    private val todoListApi = network.todoItemsApi

    private val _todoItemsRepository = TodoItemsRepository(todoListApi)
    override val todoItemsRepository: TodoItemsRepository
        get() = _todoItemsRepository

    private val _yandexAuthSdk = YandexAuthSdk.create(YandexAuthOptions(appContext))
    override val yandexAuthSdk = _yandexAuthSdk

    private val _authRepository = AuthRepository(_prefsDataStore)
    override val authRepository: AuthRepository
        get() = _authRepository

}