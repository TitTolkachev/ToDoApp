package com.example.todoapp.core.database.util

import androidx.room.TypeConverter
import com.example.todoapp.core.model.Importance

object DbConverters {

    @TypeConverter
    fun importanceToNumber(value: Importance): Int = value.ordinal

    @TypeConverter
    fun numberToImportance(value: Int): Importance? =
        Importance.entries.find { it.ordinal == value }
}