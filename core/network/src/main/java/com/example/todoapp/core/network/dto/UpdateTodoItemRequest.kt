package com.example.todoapp.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoItemRequest(
    val element: TodoItem,
)
