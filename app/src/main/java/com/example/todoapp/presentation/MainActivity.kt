package com.example.todoapp.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.presentation.navigation.RootNavGraph
import com.example.todoapp.presentation.navigation.Screen
import com.example.todoapp.presentation.theme.AppTheme

/**
 * Главня [ComponentActivity] приложения.
 */
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>(factoryProducer = { MainViewModel.Factory })

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
            AppTheme {
                RootNavGraph(
                    navController = rememberNavController(),
                    startDestination = startDestination.route
                )
            }
        }
    }
}