package com.example.todoapp.data.repository

import com.example.todoapp.data.local.PrefsDataStore

class AuthRepository(private val prefsDataStore: PrefsDataStore) {

    suspend fun getTokens() = prefsDataStore.tokenFlow

    suspend fun updateTokens(newAccessToken: String? = null) = prefsDataStore.updateTokens(newAccessToken)
}