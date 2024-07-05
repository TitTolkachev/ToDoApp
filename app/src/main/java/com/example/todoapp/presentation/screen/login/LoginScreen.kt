package com.example.todoapp.presentation.screen.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.presentation.theme.AppTheme
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk

@Composable
fun LoginScreen() {
    val viewModel = viewModel<LoginViewModel>(factory = LoginViewModel.Factory)

    val context = LocalContext.current
    val sdk = YandexAuthSdk.create(YandexAuthOptions(context, loggingEnabled = false))
    val launcher = rememberLauncherForActivityResult(sdk.contract) { result ->
        viewModel.onLogin(result)
    }



    val loginOptions = YandexAuthLoginOptions()

    Screen(
        onLoginClick = { launcher.launch(loginOptions) }
    )
}

@Composable
private fun Screen(
    onLoginClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = onLoginClick) {
            Text(text = "Войти")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Screen()
    }
}