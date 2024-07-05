package com.example.todoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.App
import com.example.todoapp.data.repository.AuthRepository
import com.example.todoapp.presentation.navigation.Screen
import com.yandex.authsdk.YandexAuthSdk
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(
    private val yandexAuthSdk: YandexAuthSdk,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    private val _startScreen: MutableStateFlow<Screen?> = MutableStateFlow(null)
    val startScreen = _startScreen.asStateFlow()

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
                val yandexAuthSdk = application.container.yandexAuthSdk
                val authRepository = application.container.authRepository
                MainViewModel(yandexAuthSdk, authRepository)
            }
        }
    }
}