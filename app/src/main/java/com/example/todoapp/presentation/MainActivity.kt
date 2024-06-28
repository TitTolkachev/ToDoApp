package com.example.todoapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.presentation.navigation.RootNavGraph
import com.example.todoapp.presentation.navigation.Screen
import com.example.todoapp.presentation.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AppTheme {
                RootNavGraph(
                    navController = rememberNavController(),
                    startDestination = Screen.TodoList.route
                )
            }
        }
    }
}