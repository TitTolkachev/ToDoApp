package com.example.todoapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddTodoItemResponse(
    val status: String,
    val element: TodoItem,
    val revision: Int
)
