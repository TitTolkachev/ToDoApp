package com.example.todoapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddTodoItemRequest(
    val element: TodoItem,
)
