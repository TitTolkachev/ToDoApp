package com.example.todoapp.domain.model

import java.util.Date

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
