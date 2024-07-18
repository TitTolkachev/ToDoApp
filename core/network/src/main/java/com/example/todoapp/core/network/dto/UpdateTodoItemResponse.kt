package com.example.todoapp.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoItemResponse(
    val status: String,
    val element: TodoItem,
    val revision: Int
)
