package com.example.todoapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.presentation.screen.login.LoginScreen
import com.example.todoapp.presentation.screen.todoitem.TodoItemScreen
import com.example.todoapp.presentation.screen.todolist.TodoListScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize(),
        enterTransition = { enterTransition() },
        popEnterTransition = { popEnterTransition() },
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
    ) {
        composable(
            route = Screen.TodoList.route,
        ) {
            TodoListScreen(
                navigateToItem = { todoItemId ->
                    navController.navigate(route = Screen.TodoItem.route + "?todoItemId=$todoItemId") {
                        launchSingleTop = true
                    }
                },
                navigateToItemCreate = {
                    navController.navigate(route = Screen.TodoItem.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = Screen.TodoItem.route + "?todoItemId={todoItemId}",
            arguments = listOf(
                navArgument("todoItemId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            val todoItemId = it.arguments?.getString("todoItemId")
            TodoItemScreen(
                todoItemId = todoItemId,
                navigateBack = { navController.navigateUp() },
            )
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                navigateToTodoList = {
                    navController.navigate(route = Screen.TodoList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}