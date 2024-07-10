package com.example.todoapp.data.remote

import com.example.todoapp.common.RestException
import com.example.todoapp.common.RestException.*
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Обработка ошибок при запросах к серверу.
 */
class ErrorInterceptor() : Interceptor {

    private var retryCount = 0
    private val maxRetries = 3

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.isSuccessful) {
            return response
        } else {
            val message = response.body?.string()

            val retryRequest = chain.request()
            var retryResponse: Response? = null
            var responseOK = false

            while (!responseOK && retryCount < maxRetries) {
                try {
                    retryResponse = chain.proceed(retryRequest)
                    responseOK = retryResponse.isSuccessful
                } catch (e: IOException) {
                    retryCount++
                    if (retryCount >= maxRetries) {
                        break
                    }
                }
            }

            val exception = when (retryResponse?.code) {
                400 -> BadCredentials
                401 -> NotAuthorize
                404 -> NotFound
                500 -> InternalServerError(message?.ifBlank { "Ошибка сервера" })
                else -> UnexpectedRest(message?.ifBlank { "Пустой ответ" })
            }

            retryResponse?.close()
            throw exception
        }
    }
}