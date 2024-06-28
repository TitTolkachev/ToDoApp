package com.example.todoapp.presentation.screen.todoitem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.model.Importance
import com.example.todoapp.data.model.Importance.HIGH
import com.example.todoapp.data.model.Importance.LOW
import com.example.todoapp.data.model.Importance.MEDIUM
import com.example.todoapp.presentation.common.noRippleClickable
import com.example.todoapp.presentation.screen.todoitem.TodoItemViewModel.Companion.Factory
import com.example.todoapp.presentation.screen.todoitem.model.TodoItemScreenMode.EDIT
import com.example.todoapp.presentation.theme.AppTheme

@Composable
fun TodoItemScreen(
    todoItemId: String? = null,
    navigateBack: () -> Unit,
) {
    val viewModel: TodoItemViewModel = viewModel(factory = Factory(todoItemId))

    LaunchedEffect(Unit) {
        viewModel.navigateBack.collect {
            navigateBack()
        }
    }

    Screen(
        text = viewModel.text.collectAsState().value,
        importance = viewModel.importance.collectAsState().value,
        deadline = viewModel.deadline.collectAsState().value,
        deleteEnabled = { viewModel.mode == EDIT },

        onNavigateBackClick = navigateBack,
        onSaveClick = { viewModel.saveItem() },
        onDeleteClick = { viewModel.deleteItem() },
        onTextChange = { viewModel.onTextChange(it) },
        onImportanceChange = { viewModel.onImportanceChange(it) },
        onDeadlineChange = { viewModel.onDeadlineChange(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    text: String = "",
    importance: Importance = HIGH,
    deadline: String? = null,
    deleteEnabled: () -> Boolean = { false },

    onNavigateBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onTextChange: (text: String) -> Unit = {},
    onImportanceChange: (importance: Importance) -> Unit = {},
    onDeadlineChange: (deadline: Long?) -> Unit = {},
) {
    Scaffold(
        topBar = {
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
        },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            var dropDownMenuVisible by remember { mutableStateOf(false) }
            var datePickerVisible by remember { mutableStateOf(false) }

            Spacer(Modifier.height(16.dp))

            Card {
                TextField(
                    value = text,
                    onValueChange = onTextChange,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            Box {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .noRippleClickable { dropDownMenuVisible = true }
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
                    onDismissRequest = { dropDownMenuVisible = false }
                ) {
                    DropdownMenuItem(text = {
                        Text(text = "Нет")
                    }, onClick = { onImportanceChange(MEDIUM); dropDownMenuVisible = false })
                    DropdownMenuItem(text = {
                        Text(text = "Низкий")
                    }, onClick = { onImportanceChange(LOW); dropDownMenuVisible = false })
                    DropdownMenuItem(text = {
                        Text(text = "Высокий")
                    }, onClick = { onImportanceChange(HIGH); dropDownMenuVisible = false })
                }
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            if (datePickerVisible) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = { datePickerVisible = false },
                    confirmButton = {
                        TextButton(onClick = {
                            onDeadlineChange(datePickerState.selectedDateMillis)
                            datePickerVisible = false
                        }) {
                            Text(text = "ПРИМЕНИТЬ")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { datePickerVisible = false }) {
                            Text(text = "ОТМЕНА")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

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
                        if (it) datePickerVisible = true
                        else onDeadlineChange(null)
                    }
                )
            }

            if (deleteEnabled()) {
                Spacer(Modifier.height(16.dp))
                HorizontalDivider()
                TextButton(onClick = onDeleteClick) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Удалить TODO элемент"
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(text = "Удалить")
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        Screen()
    }
}