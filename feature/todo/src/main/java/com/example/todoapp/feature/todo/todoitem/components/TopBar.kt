package com.example.todoapp.feature.todo.todoitem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    saving: Boolean,
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
            AnimatedVisibility(visible = saving) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp
                )
            }
            Spacer(Modifier.width(8.dp))
            TextButton(onClick = onSaveClick) {
                Text(
                    text = "Сохранить".uppercase(),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}