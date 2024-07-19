package com.example.todoapp.feature.todo.todoitem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun DeadlineBlock(
    deadline: String?,
    datePickerVisible: Boolean,
    onDeadlineChange: (deadline: Long?) -> Unit,
    showDatePicker: () -> Unit = {},
) {
    Row {
        Column {
            Text(
                text = "Сделать до",
                style = MaterialTheme.typography.bodyLarge,
            )
            if (!deadline.isNullOrBlank()) {
                Text(
                    text = deadline,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Switch(
            checked = !deadline.isNullOrBlank() || datePickerVisible,
            onCheckedChange = {
                if (it) showDatePicker()
                else onDeadlineChange(null)
            }
        )
    }
}