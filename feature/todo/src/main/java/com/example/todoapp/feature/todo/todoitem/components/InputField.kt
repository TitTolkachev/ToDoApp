package com.example.todoapp.feature.todo.todoitem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.todoapp.core.designsystem.theme.AppTheme

@Composable
fun InputField(text: String, onTextChange: (text: String) -> Unit) {
    Card {
        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            placeholder = { PlaceholderText() }
        )
    }
}

@Composable
private fun PlaceholderText() {
    Text(
        text = "Заметка...",
        color = MaterialTheme.colorScheme.outline
    )
}

@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        InputField(
            text = "Test",
            onTextChange = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview2() {
    AppTheme {
        InputField(
            text = "",
            onTextChange = {}
        )
    }
}