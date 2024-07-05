package com.example.todoapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoListRequest(
    val list: List<TodoItem>
)
