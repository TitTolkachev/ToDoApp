package com.example.todoapp.data.repository

import com.example.todoapp.data.local.PrefsDataStore
import com.example.todoapp.domain.repository.AuthRepository
import javax.inject.Inject

/** Реализация [AuthRepository]. */
class AuthRepositoryImpl @Inject constructor(
    private val store: PrefsDataStore
) : AuthRepository {

    override suspend fun getTokens() = store.tokenFlow

    override suspend fun updateToken(token: String?) = store.updateToken(token)
}