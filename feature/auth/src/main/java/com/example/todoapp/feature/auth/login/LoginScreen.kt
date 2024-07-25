package com.example.todoapp.feature.auth.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.core.designsystem.theme.AppTheme
import com.example.todoapp.feature.auth.R
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk

@Composable
fun LoginScreen(
    navigateToTodoList: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()

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
        snackbarHostState = snackbarHostState,
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
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
                .fillMaxSize()
                .semantics { isTraversalGroup = true },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.semantics {
                    heading()
                    traversalIndex = 1f
                },
                text = stringResource(R.string.title),
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.semantics { traversalIndex = 2f },
                text = stringResource(R.string.welcome),
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.semantics { traversalIndex = 4f },
                onClick = onLoginClick
            ) {
                Text(text = stringResource(R.string.enter))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.semantics { traversalIndex = 3f },
                text = stringResource(R.string.use_yandex_id),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))
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