package com.example.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для работы с аутентификацией.
 */
interface AuthRepository {
    suspend fun getTokens(): Flow<String?>
    suspend fun updateToken(token: String?)
}