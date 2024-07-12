package com.example.todoapp.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "app-db"
    ).build()

    @Provides
    @Singleton
    fun provideTodoItemDao(database: AppDatabase) = database.todoItemDao()
}