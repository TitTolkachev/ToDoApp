package com.example.todoapp.feature.todo.todoitem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.todoapp.core.designsystem.common.noRippleClickable
import com.example.todoapp.core.model.Importance
import com.example.todoapp.core.model.Importance.HIGH
import com.example.todoapp.core.model.Importance.LOW
import com.example.todoapp.core.model.Importance.MEDIUM

@Composable
internal fun ImportanceBlock(
    importance: Importance,
    onClick: () -> Unit
) {
    Box {
        Column(
            Modifier
                .fillMaxWidth()
                .noRippleClickable { onClick() }
        ) {
            Text(
                text = "Важность",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = when (importance) {
                    LOW -> "Низкий"
                    MEDIUM -> "Нет"
                    HIGH -> "Высокий"
                },
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    ImportanceBlock(
        importance = MEDIUM,
        onClick = {}
    )
}