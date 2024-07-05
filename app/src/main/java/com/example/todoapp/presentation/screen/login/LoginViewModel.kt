package com.example.todoapp.presentation.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.App
import com.example.todoapp.data.repository.AuthRepository
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import kotlinx.coroutines.launch

class LoginViewModel(
    private val yandexAuthSdk: YandexAuthSdk,
    private val authRepository: AuthRepository,
) : ViewModel() {

    fun onLogin(result: YandexAuthResult) {
        when (result) {
            YandexAuthResult.Cancelled -> Log.d("TEST", "onLogin: ${result}")
            is YandexAuthResult.Failure -> Log.d("TEST", "onLogin: ${result.exception}")
            is YandexAuthResult.Success -> viewModelScope.launch {
                authRepository.updateTokens(result.token.value)
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
                LoginViewModel(yandexAuthSdk, authRepository)
            }
        }
    }
}