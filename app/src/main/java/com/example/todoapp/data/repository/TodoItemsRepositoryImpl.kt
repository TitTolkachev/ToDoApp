package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoItemDao
import com.example.todoapp.data.remote.TodoItemApi
import com.example.todoapp.domain.model.TodoItem
import com.example.todoapp.domain.repository.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID
import com.example.todoapp.data.local.entity.TodoItem as TodoItemEntity

class TodoItemsRepositoryImpl(
    private val todoItemApi: TodoItemApi,
    private val todoItemDao: TodoItemDao,
) : TodoItemsRepository {

    override fun getItems() = todoItemDao.getAll().map { list ->
        list.map(TodoItemEntity::toDomain)
    }.flowOn(Dispatchers.Default)

    override suspend fun createItem(item: TodoItem) = withContext(Dispatchers.IO) {
        val newItem = item.copy(
            id = UUID.randomUUID().toString(),
            text = item.text.trim(),
            done = false,
            createdAt = Date(),
            changedAt = Date(),
        )
        todoItemDao.insert(newItem.toEntity())
    }

    override suspend fun getItem(itemId: String): TodoItem? = withContext(Dispatchers.IO) {

        return@withContext todoItemDao.get(itemId)?.toDomain()
    }

    override suspend fun updateItem(item: TodoItem) = withContext(Dispatchers.IO) {
        val updatedItem = item.copy(
            text = item.text.trim(),
            changedAt = Date(),
        )
        todoItemDao.upsert(updatedItem.toEntity())
    }

    override suspend fun deleteItem(todoItemId: String) = withContext(Dispatchers.IO) {
        todoItemDao.delete(todoItemId)
    }
}

private fun TodoItem.toEntity() = TodoItemEntity(
    id = id,
    text = text,
    importance = importance,
    deadline = deadline?.time,
    done = done,
    color = null,
    createdAt = createdAt.time,
    changedAt = changedAt?.time,
    lastUpdatedBy = "",
)