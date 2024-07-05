package com.example.todoapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetTodoListResponse(
    val status: String,
    val list: List<TodoItem>,
    val revision: Int
)