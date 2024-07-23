package com.example.todoapp.core.network

import com.example.todoapp.core.network.RestException.BadCredentials
import com.example.todoapp.core.network.RestException.InternalServerError
import com.example.todoapp.core.network.RestException.NotAuthorize
import com.example.todoapp.core.network.RestException.NotFound
import com.example.todoapp.core.network.RestException.UnexpectedRest
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Обработка ошибок при запросах к серверу.
 */
class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.isSuccessful) {
            return response
        } else {
            val exception = when (response.code) {
                400 -> BadCredentials
                401 -> NotAuthorize
                404 -> NotFound
                500 -> InternalServerError("Ошибка сервера")
                else -> UnexpectedRest("Непредвиденная ошибка")
            }

            throw exception
        }
    }
}