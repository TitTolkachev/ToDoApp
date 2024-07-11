package com.example.todoapp.di

import com.example.todoapp.data.repository.AuthRepositoryImpl
import com.example.todoapp.data.repository.TodoItemsRepositoryImpl
import com.example.todoapp.domain.repository.AuthRepository
import com.example.todoapp.domain.repository.TodoItemsRepository
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
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun provideTodoItemsRepository(impl: TodoItemsRepositoryImpl): TodoItemsRepository
}