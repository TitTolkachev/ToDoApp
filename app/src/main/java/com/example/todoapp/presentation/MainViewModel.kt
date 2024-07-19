package com.example.todoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.core.data.AuthRepository
import com.example.todoapp.core.datastore.PrefsDataStore
import com.example.todoapp.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    prefsDataStore: PrefsDataStore,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    private val _startScreen: MutableStateFlow<Screen?> = MutableStateFlow(null)
    val startScreen = _startScreen.asStateFlow()

    val theme = prefsDataStore.appTheme.stateIn(viewModelScope, WhileSubscribed(), null)

    init {
        checkAuth()
    }

    private fun checkAuth() {
        viewModelScope.launch(exceptionHandler) {
            val token = authRepository.getTokens().firstOrNull()
            if (token == null) {
                _startScreen.value = Screen.Login
            } else {
                _startScreen.value = Screen.TodoList
            }
        }
    }
}