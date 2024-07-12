package com.example.todoapp.core.datastore

import android.content.Context
import android.provider.Settings
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

/**
 * Предоставляет методы для работы с локальным key-value хранилищем.
 */
class PrefsDataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

    suspend fun updateToken(token: String? = null) {
        context.dataStore.edit { prefs ->
            token?.let {
                prefs[YANDEX_AUTH_TOKEN] = token
            }
        }
    }

    suspend fun updateRevision(revision: Int? = null) {
        context.dataStore.edit { prefs ->
            prefs[LAST_KNOWN_REVISION] = revision ?: 0
        }
    }

    val tokenFlow = context.dataStore.data.map { prefs ->
        prefs[YANDEX_AUTH_TOKEN]
    }

    val revision = context.dataStore.data.map { prefs ->
        prefs[LAST_KNOWN_REVISION] ?: 0
    }

    val userId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)

    companion object {
        private val YANDEX_AUTH_TOKEN = stringPreferencesKey("access_token")
        private val LAST_KNOWN_REVISION = intPreferencesKey("last_known_revision")
    }
}