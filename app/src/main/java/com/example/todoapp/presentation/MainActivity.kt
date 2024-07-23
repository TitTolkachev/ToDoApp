package com.example.todoapp.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.core.designsystem.theme.AppTheme
import com.example.todoapp.core.model.AppTheme.DARK
import com.example.todoapp.core.model.AppTheme.LIGHT
import com.example.todoapp.core.model.AppTheme.SYSTEM
import com.example.todoapp.presentation.navigation.RootNavGraph
import com.example.todoapp.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

/**
 * Главня [ComponentActivity] приложения.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        waitForAuthCheck()
    }

    private fun waitForAuthCheck() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    val startDestination = viewModel.startScreen.value
                    return if (startDestination != null) {
                        initNavGraph(startDestination)
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
    }

    private fun initNavGraph(startDestination: Screen) {
        setContent {
            val theme by viewModel.theme.collectAsState()
            CompositionLocalProvider(
                LocalConfiguration provides LocalConfiguration.current.apply {
                    this.uiMode = when (theme) {
                        LIGHT -> UI_MODE_NIGHT_NO
                        DARK -> UI_MODE_NIGHT_YES
                        SYSTEM, null -> resources.configuration.uiMode
                    }
                }
            ) {
                AppTheme(
                    darkTheme = when (theme) {
                        LIGHT -> false
                        DARK -> true
                        SYSTEM, null -> isSystemInDarkTheme()
                    }
                ) {
                    RootNavGraph(
                        navController = rememberNavController(),
                        startDestination = startDestination.route
                    )
                }
            }
        }
    }
}