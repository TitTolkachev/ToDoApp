package com.example.todoapp.presentation.screen.todoitem.components

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
import com.example.todoapp.data.model.Importance
import com.example.todoapp.presentation.common.noRippleClickable

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
                    Importance.LOW -> "Низкий"
                    Importance.MEDIUM -> "Нет"
                    Importance.HIGH -> "Высокий"
                },
                style = MaterialTheme.typography.labelMedium,
            )
        }
        DropdownMenu(
            expanded = dropDownMenuVisible,
            onDismissRequest = { changeMenuVisibility(false) }
        ) {
            DropdownMenuItem(text = {
                Text(text = "Нет")
            }, onClick = { onImportanceChange(Importance.MEDIUM); changeMenuVisibility(false) })
            DropdownMenuItem(text = {
                Text(text = "Низкий")
            }, onClick = { onImportanceChange(Importance.LOW); changeMenuVisibility(false) })
            DropdownMenuItem(text = {
                Text(text = "Высокий")
            }, onClick = { onImportanceChange(Importance.HIGH); changeMenuVisibility(false) })
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
        importance = Importance.MEDIUM,
        onImportanceChange = {},
        changeMenuVisibility = {}
    )
}