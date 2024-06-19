package com.example.todoapp.presentation.navigation

sealed class Screen(val route: String) {
    data object TodoList : Screen("todo_list")
    data object TodoItem : Screen("todo_item")
}