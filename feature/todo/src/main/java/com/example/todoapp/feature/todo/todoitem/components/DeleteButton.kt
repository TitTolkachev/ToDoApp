package com.example.todoapp.feature.todo.todoitem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.todoapp.core.designsystem.theme.AppTheme
import com.example.todoapp.feature.todo.R

@Composable
internal fun DeleteButton(deleting: Boolean, onDeleteClick: () -> Unit) {
    Row(verticalAlignment = CenterVertically) {
        TextButton(
            enabled = !deleting,
            onClick = onDeleteClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.error,
                disabledContentColor = MaterialTheme.colorScheme.outline,
            )
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Rounded.Delete,
                contentDescription = stringResource(R.string.todo_item_delete_description)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                modifier = Modifier.clearAndSetSemantics { },
                text = "Удалить"
            )
        }
        Spacer(Modifier.width(8.dp))
        AnimatedVisibility(visible = deleting) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 3.dp
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DeleteButton(deleting = false, onDeleteClick = {})
        }
    }
}