package com.example.todoapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoItemRequest(
    val element: TodoItem,
)
