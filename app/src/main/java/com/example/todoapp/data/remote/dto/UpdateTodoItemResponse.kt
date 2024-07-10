package com.example.todoapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoItemResponse(
    val status: String,
    val element: TodoItem,
    val revision: Int
)
