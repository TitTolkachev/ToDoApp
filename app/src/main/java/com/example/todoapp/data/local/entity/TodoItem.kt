package com.example.todoapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.domain.model.Importance
import java.util.Date
import com.example.todoapp.domain.model.TodoItem as TodoItemDomain

@Entity(tableName = "todo_item")
data class TodoItem(
    @PrimaryKey val id: String,
    val text: String,
    val importance: Importance,
    val deadline: Long? = null,
    val done: Boolean,
    val color: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "changed_at") val changedAt: Long? = null,
    @ColumnInfo(name = "last_updated_by") val lastUpdatedBy: String,
) {
    fun toDomain() = TodoItemDomain(
        id = id,
        text = text,
        importance = importance,
        deadline = deadline.toDate(),
        done = done,
        color = color,
        createdAt = Date(createdAt),
        changedAt = changedAt.toDate(),
    )
}

private fun Long?.toDate() = this?.let { Date(it) }