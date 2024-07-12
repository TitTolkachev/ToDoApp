package com.example.todoapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.core.database.dao.TodoItemDao
import com.example.todoapp.core.database.entity.TodoItem
import com.example.todoapp.core.database.util.DbConverters

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