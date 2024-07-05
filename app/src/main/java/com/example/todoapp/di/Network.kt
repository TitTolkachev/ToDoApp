package com.example.todoapp.di

import com.example.todoapp.data.local.PrefsDataStore
import com.example.todoapp.data.remote.TodoItemApi
import com.example.todoapp.data.remote.TokenInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class Network(private val dataStore: PrefsDataStore) {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val retrofit by lazy {
        val tokenInterceptor = TokenInterceptor(dataStore)

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .callTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        Retrofit.Builder()
            .baseUrl("https://hive.mrdekk.ru/todo/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .build()
    }

    val todoItemsApi: TodoItemApi by lazy {
        retrofit.create(TodoItemApi::class.java)
    }
}