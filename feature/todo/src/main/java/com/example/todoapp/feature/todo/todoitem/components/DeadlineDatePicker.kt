package com.example.todoapp.feature.todo.todoitem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun DeadlineDatePicker(
    datePickerVisible: Boolean,
    onDeadlineChange: (deadline: Long?) -> Unit,
    onDismissRequest: () -> Unit = {},
) {
    if (datePickerVisible) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = {
                    onDeadlineChange(datePickerState.selectedDateMillis)
                    onDismissRequest()
                }) {
                    Text(text = "ПРИМЕНИТЬ")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "ОТМЕНА")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}