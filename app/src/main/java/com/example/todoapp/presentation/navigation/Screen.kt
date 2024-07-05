package com.example.todoapp.presentation.navigation

/**
 * Экраны приложения.
 */
sealed class Screen(val route: String) {
    data object TodoList : Screen("todo_list")
    data object TodoItem : Screen("todo_item")
    data object Login : Screen("login")
}