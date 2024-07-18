package com.example.todoapp.core.network.dto

import com.example.todoapp.core.model.Importance
import com.example.todoapp.core.model.Importance.HIGH
import com.example.todoapp.core.model.Importance.LOW
import com.example.todoapp.core.model.Importance.MEDIUM
import kotlinx.serialization.Serializable
import java.util.Date
import com.example.todoapp.core.model.TodoItem as TodoItemDomain

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
    val files: List<String>? = null,
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

fun TodoItemDomain.toDto(userId: String) = TodoItem(
    id = id,
    text = text,
    importance = importance.toDtoImportance(),
    deadline = deadline?.time,
    done = done,
    color = color,
    created_at = createdAt.time,
    changed_at = changedAt?.time,
    last_updated_by = userId,
)

private fun String.toDomainImportance() = when (this) {
    "low" -> LOW
    "basic" -> MEDIUM
    "important" -> HIGH
    else -> MEDIUM
}

private fun Importance.toDtoImportance() = when (this) {
    LOW -> "low"
    MEDIUM -> "basic"
    HIGH -> "important"
}

private fun Long?.toDate() = this?.let { Date(it) }