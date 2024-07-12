package com.example.todoapp.core.data

import com.example.todoapp.core.model.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Репозиторий для работы с сущностью TodoItem.
 */
interface TodoItemsRepository {

    val dataIsActual: StateFlow<Boolean?>

    fun getItems(): Flow<List<com.example.todoapp.core.model.TodoItem>>

    suspend fun createItem(item: com.example.todoapp.core.model.TodoItem)

    suspend fun getItem(itemId: String): com.example.todoapp.core.model.TodoItem?

    suspend fun updateItem(item: com.example.todoapp.core.model.TodoItem)

    suspend fun deleteItem(todoItemId: String)

    suspend fun sync()
}