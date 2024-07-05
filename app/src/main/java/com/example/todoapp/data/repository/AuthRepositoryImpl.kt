package com.example.todoapp.data.repository

import com.example.todoapp.data.local.PrefsDataStore
import com.example.todoapp.domain.repository.AuthRepository

class AuthRepositoryImpl(private val store: PrefsDataStore) : AuthRepository {

    override suspend fun getTokens() = store.tokenFlow

    override suspend fun updateToken(token: String?) = store.updateToken(token)
}