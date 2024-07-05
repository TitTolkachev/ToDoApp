package com.example.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun getTokens(): Flow<String?>
    suspend fun updateToken(token: String?)
}