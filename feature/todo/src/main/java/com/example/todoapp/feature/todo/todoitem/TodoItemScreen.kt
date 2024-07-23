package com.example.todoapp.feature.todo.todoitem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.todoapp.feature.todo.todoitem.components.DeleteButton
import com.example.todoapp.feature.todo.todoitem.components.ImportanceBlock
import com.example.todoapp.feature.todo.todoitem.components.ImportanceBottomSheet
import com.example.todoapp.feature.todo.todoitem.components.InputField
import com.example.todoapp.feature.todo.todoitem.components.TopBar
import com.example.todoapp.feature.todo.todoitem.model.TodoItemScreenMode.EDIT
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemScreen(
    navigateBack: () -> Unit,
) {
    val viewModel: TodoItemViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    var importanceBottomSheetVisible by remember { mutableStateOf(false) }
    val importanceBottomSheetState = rememberModalBottomSheetState()

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

    var importanceBlockHighlighted by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    ImportanceBottomSheet(
        visible = importanceBottomSheetVisible,
        sheetState = importanceBottomSheetState,
        onDismissRequest = { importanceBottomSheetVisible = false },
        onImportanceChange = {
            viewModel.onImportanceChange(it)
            if (it == HIGH) {
                scope.launch {
                    importanceBlockHighlighted = true
                    delay(400)
                    importanceBlockHighlighted = false
                }
            }
        },
        onHide = {
            scope.launch { importanceBottomSheetState.hide() }.invokeOnCompletion {
                if (!importanceBottomSheetState.isVisible) {
                    importanceBottomSheetVisible = false
                }
            }
        },
    )

    Screen(
        text = viewModel.text.collectAsStateWithLifecycle().value,
        importance = viewModel.importance.collectAsStateWithLifecycle().value,
        deadline = viewModel.deadline.collectAsStateWithLifecycle().value,
        saving = viewModel.saving.collectAsStateWithLifecycle().value,
        deleting = viewModel.deleting.collectAsStateWithLifecycle().value,
        deleteEnabled = { viewModel.mode == EDIT },
        importanceBlockHighlighted = importanceBlockHighlighted,
        snackbarHostState = snackbarHostState,

        onNavigateBackClick = navigateBack,
        onSaveClick = { viewModel.saveItem() },
        onDeleteClick = { viewModel.deleteItem() },
        onTextChange = { viewModel.onTextChange(it) },
        onDeadlineChange = { viewModel.onDeadlineChange(it) },
        onImportanceBlockClick = { importanceBottomSheetVisible = true }
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
    importanceBlockHighlighted: Boolean = false,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),

    onNavigateBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onTextChange: (text: String) -> Unit = {},
    onDeadlineChange: (deadline: Long?) -> Unit = {},
    onImportanceBlockClick: () -> Unit = {},
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
            var datePickerVisible by remember { mutableStateOf(false) }

            Spacer(Modifier.height(16.dp))

            InputField(text, onTextChange)

            Spacer(Modifier.height(16.dp))

            ImportanceBlock(
                highlighted = importanceBlockHighlighted,
                importance = importance,
                onClick = onImportanceBlockClick,
            )

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
                DeleteButton(deleting, onDeleteClick)
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