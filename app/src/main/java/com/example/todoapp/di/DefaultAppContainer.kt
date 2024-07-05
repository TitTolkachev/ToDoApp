package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.local.AppDatabase
import com.example.todoapp.data.local.PrefsDataStore
import com.example.todoapp.data.repository.AuthRepositoryImpl
import com.example.todoapp.data.repository.TodoItemsRepositoryImpl
import com.example.todoapp.domain.repository.AuthRepository
import com.example.todoapp.domain.repository.TodoItemsRepository

/**
 * Буду с ручным DI, пока не изучу Dagger.
 * https://developer.android.com/training/dependency-injection/manual
 */
class DefaultAppContainer(private val appContext: Context) : AppContainer {

    // Database
    private val db = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java, "app-db"
    ).build()
    private val todoItemsDao = db.todoItemDao()

    // DataStore
    private val prefsDataStore = PrefsDataStore(appContext)

    // Network
    private val network = Network(prefsDataStore)
    private val todoListApi = network.todoItemsApi

    // Repositories
    private val _todoItemsRepository = TodoItemsRepositoryImpl(todoListApi, todoItemsDao)
    override val todoItemsRepository: TodoItemsRepository
        get() = _todoItemsRepository

    private val _authRepository = AuthRepositoryImpl(prefsDataStore)
    override val authRepository: AuthRepository
        get() = _authRepository
}