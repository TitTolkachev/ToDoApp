package com.example.todoapp.data.remote.dto

import com.example.todoapp.presentation.model.Importance
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class TodoItem(
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long?,
    val done: Boolean,
    val color: String?,
    val created_at: Long,
    val changed_at: Long?,
    val last_updated_by: String,
)