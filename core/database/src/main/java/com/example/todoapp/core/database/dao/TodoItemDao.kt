package com.example.todoapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.todoapp.core.database.entity.TodoItem
import kotlinx.coroutines.flow.Flow

/**
 * Предоставляет методы для работы с сущностью TodoItem в БД.
 */
@Dao
interface TodoItemDao {

    @Query("SELECT * FROM todo_item")
    fun getAll(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo_item WHERE id = :itemId")
    suspend fun get(itemId: String): TodoItem?

    @Insert
    suspend fun insertAll(items: List<TodoItem>)

    @Insert
    suspend fun insert(item: TodoItem)

    @Upsert
    suspend fun upsert(item: TodoItem)

    @Query("DELETE FROM todo_item WHERE id = :itemId")
    suspend fun delete(itemId: String)

    @Query("DELETE FROM todo_item")
    suspend fun deleteAll()

    @Transaction
    suspend fun replace(items: List<TodoItem>) {
        deleteAll()
        insertAll(items)
    }
}