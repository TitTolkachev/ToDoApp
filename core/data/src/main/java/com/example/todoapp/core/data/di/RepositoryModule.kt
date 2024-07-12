package com.example.todoapp.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideAuthRepository(impl: com.example.todoapp.core.data.AuthRepositoryImpl): com.example.todoapp.core.data.AuthRepository

    @Binds
    @Singleton
    fun provideTodoItemsRepository(impl: com.example.todoapp.core.data.TodoItemsRepositoryImpl): com.example.todoapp.core.data.TodoItemsRepository
}