package com.example.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.data.local.dao.TodoItemDao
import com.example.todoapp.data.local.entity.TodoItem
import com.example.todoapp.data.local.util.DbConverters

/**
 * Представляет БД приложения.
 * Используется для определения конфигурации БД.
 */
@Database(
    entities = [TodoItem::class],
    version = 1
)
@TypeConverters(DbConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao
}