package com.example.todoapp.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoListRequest(
    val list: List<TodoItem>
)
