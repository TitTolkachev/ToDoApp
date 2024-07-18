package com.example.todoapp.core.network.di

import com.example.todoapp.core.datastore.PrefsDataStore
import com.example.todoapp.core.network.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetwork(dataStore: PrefsDataStore): Network = Network(dataStore)

    @Provides
    @Singleton
    fun provideTodoItemsApi(network: Network) = network.todoItemsApi
}