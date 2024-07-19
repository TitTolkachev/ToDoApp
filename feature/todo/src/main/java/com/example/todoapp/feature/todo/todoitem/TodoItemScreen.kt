package com.example.todoapp.feature.todo.todoitem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.core.designsystem.theme.AppTheme
import com.example.todoapp.core.model.Importance
import com.example.todoapp.core.model.Importance.HIGH
import com.example.todoapp.feature.todo.todoitem.components.DeadlineBlock
import com.example.todoapp.feature.todo.todoitem.components.DeadlineDatePicker
import com.example.todoapp.feature.todo.todoitem.components.ImportanceBlock
import com.example.todoapp.feature.todo.todoitem.components.InputField
import com.example.todoapp.feature.todo.todoitem.components.TopBar
import com.example.todoapp.feature.todo.todoitem.model.TodoItemScreenMode.EDIT

@Composable
fun TodoItemScreen(
    navigateBack: () -> Unit,
) {
    val viewModel: TodoItemViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navigateBack.collect {
            navigateBack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.showSnackbar.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Screen(
        text = viewModel.text.collectAsStateWithLifecycle().value,
        importance = viewModel.importance.collectAsStateWithLifecycle().value,
        deadline = viewModel.deadline.collectAsStateWithLifecycle().value,
        saving = viewModel.saving.collectAsStateWithLifecycle().value,
        deleting = viewModel.deleting.collectAsStateWithLifecycle().value,
        deleteEnabled = { viewModel.mode == EDIT },
        snackbarHostState = snackbarHostState,

        onNavigateBackClick = navigateBack,
        onSaveClick = { viewModel.saveItem() },
        onDeleteClick = { viewModel.deleteItem() },
        onTextChange = { viewModel.onTextChange(it) },
        onImportanceChange = { viewModel.onImportanceChange(it) },
        onDeadlineChange = { viewModel.onDeadlineChange(it) },
    )
}

@Composable
private fun Screen(
    text: String = "",
    importance: Importance = HIGH,
    deadline: String? = null,
    saving: Boolean = false,
    deleting: Boolean = false,
    deleteEnabled: () -> Boolean = { false },
    snackbarHostState: SnackbarHostState = SnackbarHostState(),

    onNavigateBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onTextChange: (text: String) -> Unit = {},
    onImportanceChange: (importance: Importance) -> Unit = {},
    onDeadlineChange: (deadline: Long?) -> Unit = {},
) {
    Scaffold(
        topBar = { TopBar(saving, onNavigateBackClick, onSaveClick) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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

            InputField(text, onTextChange)

            Spacer(Modifier.height(16.dp))

            ImportanceBlock(
                dropDownMenuVisible,
                importance,
                onImportanceChange,
            ) { dropDownMenuVisible = it }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            DeadlineDatePicker(datePickerVisible, onDeadlineChange) { datePickerVisible = false }

            DeadlineBlock(deadline, datePickerVisible, onDeadlineChange) {
                datePickerVisible = true
            }

            if (deleteEnabled()) {
                Spacer(Modifier.height(16.dp))
                HorizontalDivider()
                Row(verticalAlignment = CenterVertically) {
                    TextButton(
                        onClick = onDeleteClick,
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "Удалить TODO элемент"
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(text = "Удалить")
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

            Spacer(Modifier.height(32.dp))
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview1() {
    AppTheme {
        Screen()
    }
}

@PreviewLightDark
@Composable
private fun Preview2() {
    AppTheme {
        Screen(
            text = "Текст",
            importance = HIGH,
            deadline = "01.01.2022",
            deleteEnabled = { true },
        )
    }
}