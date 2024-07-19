package com.example.todoapp.feature.todo.todoitem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.todoapp.core.designsystem.common.noRippleClickable
import com.example.todoapp.core.designsystem.theme.AppTheme
import com.example.todoapp.core.model.Importance
import com.example.todoapp.core.model.Importance.HIGH
import com.example.todoapp.core.model.Importance.LOW
import com.example.todoapp.core.model.Importance.MEDIUM

@Composable
internal fun ImportanceBlock(
    highlighted: Boolean,
    importance: Importance,
    onClick: () -> Unit
) {
    val color = animateColorAsState(
        targetValue = if (highlighted) MaterialTheme.colorScheme.error else Color.Unspecified,
        label = "Importance Block Color"
    )
    Box(
        Modifier
            .border(2.dp, color = color.value, shape = RoundedCornerShape(8.dp))
            .drawWithCache {
                onDrawBehind {
                    drawRoundRect(
                        color = color.value,
                        alpha = 0.1f,
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                    )
                }
            }
    ) {
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
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ImportanceBlock(
                highlighted = false,
                importance = MEDIUM,
                onClick = {}
            )
        }
    }
}