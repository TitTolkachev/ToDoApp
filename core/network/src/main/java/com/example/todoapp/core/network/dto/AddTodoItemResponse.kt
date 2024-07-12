package com.example.todoapp.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddTodoItemResponse(
    val status: String,
    val element: TodoItem,
    val revision: Int
)
