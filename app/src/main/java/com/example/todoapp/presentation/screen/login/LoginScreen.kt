package com.example.todoapp.presentation.screen.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.presentation.theme.AppTheme
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk

@Composable
fun LoginScreen(
    navigateToTodoList: () -> Unit,
) {
    val viewModel = viewModel<LoginViewModel>(factory = LoginViewModel.Factory)

    val context = LocalContext.current
    val loginOptions = YandexAuthLoginOptions()
    val sdk = YandexAuthSdk.create(YandexAuthOptions(context))
    val launcher = rememberLauncherForActivityResult(sdk.contract) { result ->
        viewModel.onLogin(result)
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navigateToTodoList.collect {
            navigateToTodoList()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.showError.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Screen(
        onLoginClick = { launcher.launch(loginOptions) }
    )
}

@Composable
private fun Screen(
    snackbarHostState: SnackbarHostState = SnackbarHostState(),

    onLoginClick: () -> Unit = {},
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Добро пожаловать в приложение TodoList!")

            Button(onClick = onLoginClick) {
                Text(text = "Войти")
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        Screen()
    }
}