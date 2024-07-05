package com.example.todoapp.data.remote.dto

import com.example.todoapp.domain.model.Importance
import kotlinx.serialization.Serializable
import java.util.Date
import com.example.todoapp.domain.model.TodoItem as TodoItemDomain

@Serializable
data class TodoItem(
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long? = null,
    val done: Boolean,
    val color: String? = null,
    val created_at: Long,
    val changed_at: Long? = null,
    val last_updated_by: String,
) {
    fun toDomain() = TodoItemDomain(
        id = id,
        text = text,
        importance = importance.toDomainImportance(),
        deadline = deadline.toDate(),
        done = done,
        color = color,
        createdAt = Date(created_at),
        changedAt = changed_at.toDate(),
    )
}

private fun String.toDomainImportance() = when (this) {
    "low" -> Importance.LOW
    "basic" -> Importance.MEDIUM
    "important" -> Importance.HIGH
    else -> Importance.MEDIUM
}

private fun Long?.toDate() = this?.let { Date(it) }