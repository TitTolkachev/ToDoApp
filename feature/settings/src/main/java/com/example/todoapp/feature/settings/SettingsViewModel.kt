package com.example.todoapp.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.core.datastore.PrefsDataStore
import com.example.todoapp.core.model.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefsDataStore: PrefsDataStore
) : ViewModel() {

    val theme = prefsDataStore.appTheme.stateIn(viewModelScope, WhileSubscribed(), null)

    fun selectTheme(theme: AppTheme) = viewModelScope.launch {
        prefsDataStore.updateTheme(theme)
    }
}