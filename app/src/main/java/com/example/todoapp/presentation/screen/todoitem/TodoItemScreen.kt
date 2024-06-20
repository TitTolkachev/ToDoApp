package com.example.todoapp.presentation.screen.todoitem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.example.todoapp.data.model.Importance.HIGH
import com.example.todoapp.data.model.Importance.LOW
import com.example.todoapp.data.model.Importance.MEDIUM
import com.example.todoapp.presentation.common.noRippleClickable
import com.example.todoapp.presentation.screen.todoitem.TodoItemViewModel.Companion.Factory
import com.example.todoapp.presentation.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun TodoItemScreen(
    backStackEntry: NavBackStackEntry,
    navigateBack: () -> Unit,
) {
    val viewModel: TodoItemViewModel = viewModel(factory = Factory(backStackEntry.savedStateHandle))

    Screen(
        deleteEnabled = false,

        onNavigateBackClick = navigateBack,
        onSaveClick = { viewModel.saveItem() },
        onDeleteClick = { viewModel.deleteItem() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    deleteEnabled: Boolean = false,

    onNavigateBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
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
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            var text by remember { mutableStateOf("") }
            Card {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            var show by remember { mutableStateOf(false) }
            var importance by remember { mutableStateOf(MEDIUM) }
            Box {
                Column(Modifier.noRippleClickable { show = true }) {
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
                    expanded = show,
                    onDismissRequest = { show = false }
                ) {
                    DropdownMenuItem(text = {
                        Text(text = "Нет")
                    }, onClick = { importance = MEDIUM; show = false })
                    DropdownMenuItem(text = {
                        Text(text = "Низкий")
                    }, onClick = { importance = LOW; show = false })
                    DropdownMenuItem(text = {
                        Text(text = "Высокий")
                    }, onClick = { importance = HIGH; show = false })
                }
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            var date by remember { mutableStateOf("") }
            var visible by remember { mutableStateOf(false) }
            val datePickerState = rememberDatePickerState()

            val dateFormat = remember {
                SimpleDateFormat("d MMMM yyyy", java.util.Locale.getDefault())
            }

            if (visible) {
                DatePickerDialog(
                    onDismissRequest = { visible = false },
                    confirmButton = {
                        TextButton(onClick = {
                            date = dateFormat.format(Date(datePickerState.selectedDateMillis ?: 0))
                            visible = false
                        }) {
                            Text(text = "ПРИМЕНИТЬ")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { visible = false }) {
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
                    if (date.isNotBlank()) {
                        Text(
                            text = date,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    checked = date.isNotBlank() || visible,
                    onCheckedChange = {
                        if (it) visible = true
                        else date = ""
                    }
                )
            }

            if (deleteEnabled) {
                HorizontalDivider()
                TextButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Удалить TODO элемент"
                    )
                    Text(text = "Удалить")
                }
            }
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