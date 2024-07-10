package com.example.todoapp.data.remote.dto

import kotlinx.serialization.Serializable
import com.example.todoapp.domain.model.TodoItem as TodoItemDomain

@Serializable
data class GetTodoListResponse(
    val status: String,
    val list: List<TodoItem>,
    val revision: Int
){
    fun toDomain() = list.map { it.toDomain() }
}