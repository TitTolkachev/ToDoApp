package com.example.todoapp.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddTodoItemRequest(
    val element: TodoItem,
)
