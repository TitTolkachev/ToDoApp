package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.data.service.InternetConnectionListener
import com.example.todoapp.core.data.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideInternetConnectionListener(
        @ApplicationContext context: Context,
        todoItemsRepository: TodoItemsRepository
    ) = InternetConnectionListener(
        context = context,
        todoItemsRepository = todoItemsRepository
    )
}