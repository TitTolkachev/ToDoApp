package com.example.todoapp.data.model

import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: Date?,
    val done: Boolean,
    val creationDate: Date,
    val updateDate: Date?,
)
