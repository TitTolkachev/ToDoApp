package com.example.todoapp.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeleteTodoItemResponse(
    val status: String,
    val element: TodoItem,
    val revision: Int
)
