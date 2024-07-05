package com.example.todoapp.data.remote

import com.example.todoapp.data.local.PrefsDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Подставляет токены в запросы, требующие авторизацию.
 */
class TokenInterceptor(
    private val dataStore: PrefsDataStore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStore.tokenFlow.firstOrNull()
        } ?: return chain.proceed(chain.request())

        val originalRequest = chain.request()
        val originalRequestWithAccessToken =
            originalRequest.newBuilder().addToken(token).build()

        return chain.proceed(originalRequestWithAccessToken)
    }

    private fun Request.Builder.addToken(token: String) =
        this.header("Authorization", "OAuth $token")
}