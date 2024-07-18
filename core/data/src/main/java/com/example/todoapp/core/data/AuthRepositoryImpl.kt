package com.example.todoapp.core.data

import com.example.todoapp.core.datastore.PrefsDataStore
import javax.inject.Inject

/** Реализация [AuthRepository]. */
class AuthRepositoryImpl @Inject constructor(
    private val store: PrefsDataStore
) : AuthRepository {

    override suspend fun getTokens() = store.tokenFlow

    override suspend fun updateToken(token: String?) = store.updateToken(token)
}