package com.example.todoapp.data.repository

import com.example.todoapp.data.local.PrefsDataStore
import com.example.todoapp.data.local.dao.TodoItemDao
import com.example.todoapp.data.local.entity.toEntity
import com.example.todoapp.data.remote.TodoItemApi
import com.example.todoapp.data.remote.dto.toDto
import com.example.todoapp.domain.model.TodoItem
import com.example.todoapp.domain.repository.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.Date
import java.util.UUID
import com.example.todoapp.data.local.entity.TodoItem as TodoItemEntity
import com.example.todoapp.data.remote.dto.AddTodoItemRequest as AddRequest
import com.example.todoapp.data.remote.dto.UpdateTodoItemRequest as UpdateRequest
import com.example.todoapp.data.remote.dto.UpdateTodoListRequest as UpdateListRequest

/** Реализация [TodoItemsRepository]. */
class TodoItemsRepositoryImpl(
    private val dataStore: PrefsDataStore,
    private val todoItemApi: TodoItemApi,
    private val todoItemDao: TodoItemDao,
) : TodoItemsRepository {

    /** Индикатор, загружены ли актуальные данные в БД. */
    private val _dataIsActual: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    override val dataIsActual = _dataIsActual.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getItems() = todoItemDao.getAll()
        .onStart {
            try {
                sync()
            } catch (_: Exception) {
            }
        }
        .flowOn(Dispatchers.IO)
        .mapLatest { it.map(TodoItemEntity::toDomain) }
        .flowOn(Dispatchers.Default)

    override suspend fun createItem(item: TodoItem) = withContext(Dispatchers.IO) {
        val newItem = item.copy(
            id = UUID.randomUUID().toString(),
            text = item.text.trim(),
            done = false,
            createdAt = Date(),
            changedAt = Date(),
        )
        todoItemDao.insert(newItem.toEntity())

        if (_dataIsActual.value == true) {
            createItemRemote(newItem)
        }
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

        if (_dataIsActual.value == true) {
            updateItemRemote(updatedItem)
        }
    }

    override suspend fun deleteItem(todoItemId: String) = withContext(Dispatchers.IO) {
        todoItemDao.delete(todoItemId)

        if (_dataIsActual.value == true) {
            deleteItemRemote(todoItemId)
        }
    }

    override suspend fun sync() {
        val revision = dataStore.revision.firstOrNull() ?: 0
        val userId = dataStore.userId

        val todoItems = todoItemDao.getAll().firstOrNull()?.map {
            it.toDomain().toDto(userId)
        } ?: return

        val response = todoItemApi.updateTodoList(revision, UpdateListRequest(todoItems))
        if (response.isSuccessful) {
            dataStore.updateRevision(response.body()!!.revision)
            todoItemDao.replace(response.body()!!.list.map { it.toDomain() }.map { it.toEntity() })
            _dataIsActual.update { true }
        } else {
            _dataIsActual.update { false }
        }
    }

    private suspend fun createItemRemote(newItem: TodoItem) {
        val revision = dataStore.revision.first()
        val userId = dataStore.userId
        val itemDto = newItem.toDto(userId)

        val response = todoItemApi.addTodoItem(revision, AddRequest(itemDto))
        if (response.isSuccessful) {
            val newRevision = response.body()!!.revision
            dataStore.updateRevision(newRevision)
        }
    }

    private suspend fun updateItemRemote(newItem: TodoItem) {
        val revision = dataStore.revision.first()
        val userId = dataStore.userId
        val itemDto = newItem.toDto(userId)

        val response = todoItemApi.updateTodoItem(revision, itemDto.id, UpdateRequest(itemDto))
        if (response.isSuccessful) {
            val newRevision = response.body()!!.revision
            dataStore.updateRevision(newRevision)
        }
    }

    private suspend fun deleteItemRemote(itemId: String) {
        val revision = dataStore.revision.first()

        val response = todoItemApi.deleteTodoItem(revision, itemId)
        if (response.isSuccessful) {
            val newRevision = response.body()!!.revision
            dataStore.updateRevision(newRevision)
        }
    }
}