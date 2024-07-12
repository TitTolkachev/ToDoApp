package com.example.todoapp.feature.todo.todoitem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.core.designsystem.common.noRippleClickable
import com.example.todoapp.core.model.Importance
import com.example.todoapp.core.model.Importance.HIGH
import com.example.todoapp.core.model.Importance.LOW
import com.example.todoapp.core.model.Importance.MEDIUM

@Composable
fun ImportanceBlock(
    dropDownMenuVisible: Boolean,
    importance: Importance,
    onImportanceChange: (importance: Importance) -> Unit,
    changeMenuVisibility: (Boolean) -> Unit
) {
    Box {
        Column(
            Modifier
                .fillMaxWidth()
                .noRippleClickable { changeMenuVisibility(true) }
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
        DropdownMenu(
            expanded = dropDownMenuVisible,
            onDismissRequest = { changeMenuVisibility(false) }
        ) {
            DropdownMenuItem(
                text = { Text(text = "Нет") },
                onClick = { onImportanceChange(MEDIUM); changeMenuVisibility(false) }
            )
            DropdownMenuItem(
                text = { Text(text = "Низкий") },
                onClick = { onImportanceChange(LOW); changeMenuVisibility(false) }
            )
            DropdownMenuItem(
                text = { Text(text = "Высокий") },
                onClick = { onImportanceChange(HIGH); changeMenuVisibility(false) }
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun Preview() {
    ImportanceBlock(
        dropDownMenuVisible = false,
        importance = MEDIUM,
        onImportanceChange = {},
        changeMenuVisibility = {}
    )
}