package com.example.todoapp.domain.repository

import com.example.todoapp.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Репозиторий для работы с сущностью TodoItem.
 */
interface TodoItemsRepository {

    val dataIsActual: StateFlow<Boolean?>

    fun getItems(): Flow<List<TodoItem>>

    suspend fun createItem(item: TodoItem)

    suspend fun getItem(itemId: String): TodoItem?

    suspend fun updateItem(item: TodoItem)

    suspend fun deleteItem(todoItemId: String)

    suspend fun sync()
}