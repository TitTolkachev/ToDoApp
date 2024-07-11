package com.example.todoapp.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.repository.AuthRepository
import com.yandex.authsdk.YandexAuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _navigateToTodoList: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val navigateToTodoList = _navigateToTodoList.asSharedFlow()

    private val _showError: MutableSharedFlow<String> = MutableSharedFlow()
    val showError = _showError.asSharedFlow()

    fun onLogin(result: YandexAuthResult) {
        when (result) {
            YandexAuthResult.Cancelled -> viewModelScope.launch {
                _showError.emit("Вход не выполнен")
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
}