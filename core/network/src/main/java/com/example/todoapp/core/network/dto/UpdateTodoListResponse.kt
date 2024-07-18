package com.example.todoapp.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoListResponse(
    val status: String,
    val list: List<TodoItem>,
    val revision: Int
)
