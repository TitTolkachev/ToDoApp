package com.example.todoapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


class PrefsDataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

    suspend fun updateToken(token: String? = null) {
        context.dataStore.edit { prefs ->
            token?.let {
                prefs[YANDEX_AUTH_TOKEN] = token
            }
        }
    }

    val tokenFlow = context.dataStore.data.map { prefs ->
        prefs[YANDEX_AUTH_TOKEN]
    }

    companion object {
        private val YANDEX_AUTH_TOKEN = stringPreferencesKey("access_token")
    }
}