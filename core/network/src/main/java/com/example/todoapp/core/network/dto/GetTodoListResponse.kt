package com.example.todoapp.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetTodoListResponse(
    val status: String,
    val list: List<TodoItem>,
    val revision: Int
){
    fun toDomain() = list.map { it.toDomain() }
}