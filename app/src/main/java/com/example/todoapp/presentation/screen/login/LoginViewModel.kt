package com.example.todoapp.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.App
import com.example.todoapp.domain.repository.AuthRepository
import com.yandex.authsdk.YandexAuthResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _navigateToTodoList: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val navigateToTodoList = _navigateToTodoList.asSharedFlow()

    private val _showError: MutableSharedFlow<String> = MutableSharedFlow()
    val showError = _showError.asSharedFlow()

    fun onLogin(result: YandexAuthResult) {
        when (result) {
            YandexAuthResult.Cancelled -> viewModelScope.launch {
                _showError.emit("Ошибка авторизации")
            }

            is YandexAuthResult.Failure -> viewModelScope.launch {
                _showError.emit(result.exception.message ?: "Ошибка авторизации")
            }

            is YandexAuthResult.Success -> viewModelScope.launch {
                authRepository.updateToken(result.token.value)
                _navigateToTodoList.emit(true)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
                val authRepository = application.container.authRepository
                LoginViewModel(authRepository)
            }
        }
    }
}