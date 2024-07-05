package com.example.todoapp.data.local.util

import androidx.room.TypeConverter
import com.example.todoapp.domain.model.Importance

object DbConverters {

    @TypeConverter
    fun importanceToNumber(value: Importance): Int = value.ordinal

    @TypeConverter
    fun numberToImportance(value: Int): Importance? =
        Importance.entries.find { it.ordinal == value }
}