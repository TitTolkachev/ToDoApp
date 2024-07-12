package com.example.todoapp.core.model

import java.util.Date

/**
 * Данные о задаче.
 */
data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: Date? = null,
    val done: Boolean,
    val color: String? = null,
    val createdAt: Date,
    val changedAt: Date? = null,
)
