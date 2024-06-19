package com.example.todoapp.presentation.screen.todolist.components

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.data.model.Importance
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.presentation.theme.AppTheme
import java.util.Date

@Composable
fun TodoListItem(
    item: TodoItem,
    onClick: () -> Unit
) {
    Card {
        Text(text = item.id)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        TodoListItem(
            item = TodoItem(
                id = "1",
                text = "Buy groceries",
                importance = Importance.MEDIUM,
                deadline = Date(Date().time - 86400000),
                done = false,
                creationDate = Date(Date().time + 86400000),
                updateDate = null
            ),
            onClick = {},
        )
    }
}