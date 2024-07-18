package com.example.todoapp.feature.todo.todoitem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    onNavigateBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onNavigateBackClick) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Закрыть экран TODO элемента",
                )
            }
        },
        actions = {
            TextButton(onClick = onSaveClick) {
                Text(
                    text = "Сохранить".uppercase(),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}