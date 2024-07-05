package com.example.todoapp.domain.repository

import com.example.todoapp.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {

    fun getItems(): Flow<List<TodoItem>>

    suspend fun createItem(item: TodoItem)

    suspend fun getItem(itemId: String): TodoItem?

    suspend fun updateItem(item: TodoItem)

    suspend fun deleteItem(todoItemId: String)
}