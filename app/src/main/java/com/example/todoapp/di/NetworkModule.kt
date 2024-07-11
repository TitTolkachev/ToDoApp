package com.example.todoapp.di

import com.example.todoapp.data.local.PrefsDataStore
import com.example.todoapp.data.remote.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetwork(dataStore: PrefsDataStore): Network = Network(dataStore)

    @Provides
    @Singleton
    fun provideTodoItemsApi(network: Network) = network.todoItemsApi
}