package com.example.todoapp.presentation.screen.todolist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.core.designsystem.theme.AppTheme
import com.example.todoapp.core.model.Importance
import com.example.todoapp.core.model.TodoItem
import com.example.todoapp.feature.todo.todolist.components.TodoListItem
import com.example.todoapp.feature.todo.todolist.model.TodoListScreenState
import com.example.todoapp.feature.todo.todolist.model.TodoListScreenState.EMPTY
import com.example.todoapp.feature.todo.todolist.model.TodoListScreenState.LOADING
import com.example.todoapp.feature.todo.todolist.model.TodoListScreenState.VIEW
import java.util.Date
import com.example.todoapp.core.designsystem.R as UiR

@Composable
fun TodoListScreen(
    navigateToItem: (todoItemId: String) -> Unit,
    navigateToItemCreate: () -> Unit,
) {
    val viewModel: TodoListViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.showSnackbar.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Screen(
        items = viewModel.items.collectAsStateWithLifecycle().value ?: emptyList(),
        showCompletedTasks = viewModel.showCompletedTasks.collectAsStateWithLifecycle().value,
        completedTasksCount = viewModel.completedTasksCount.collectAsStateWithLifecycle().value,
        screenState = viewModel.screenState.collectAsStateWithLifecycle().value,
        snackbarHostState = snackbarHostState,
        dataIsActual = viewModel.dataIsActual.collectAsStateWithLifecycle().value,

        onSyncClick = { viewModel.sync() },
        onItemClick = navigateToItem,
        onItemCheckBoxClick = { id, completed ->
            viewModel.changeItemCompletionStatus(id, completed)
        },
        onFabClick = navigateToItemCreate,
        onChangeCompletedTasksVisibilityClick = { viewModel.changeCompletedTasksVisibility() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    items: List<TodoItem> = emptyList(),
    showCompletedTasks: Boolean = true,
    completedTasksCount: Int = 0,
    screenState: TodoListScreenState = EMPTY,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    dataIsActual: Boolean? = true,

    onSyncClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {},
    onItemCheckBoxClick: (id: String, done: Boolean) -> Unit = { _, _ -> },
    onFabClick: () -> Unit = {},
    onChangeCompletedTasksVisibilityClick: () -> Unit = {},
) {
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                showCompletedTasks = showCompletedTasks,
                completedTasksCount = completedTasksCount,
                dataIsActual = dataIsActual,
                onSyncClick = onSyncClick,
                onChangeCompletedTasksVisibilityClick = onChangeCompletedTasksVisibilityClick,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFabClick) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Добавить элемент в список дел",
                )
            }
        },
    ) { paddingValues ->
        Crossfade(
            targetState = screenState,
            modifier = Modifier.padding(paddingValues),
            label = "TodoListScreen Content",
        ) {
            when (it) {
                LOADING -> ScreenLoading()
                VIEW -> ScreenView(items, onItemCheckBoxClick, onItemClick)
                EMPTY -> ScreenEmpty()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    showCompletedTasks: Boolean,
    completedTasksCount: Int,
    dataIsActual: Boolean? = true,
    onSyncClick: () -> Unit,
    onChangeCompletedTasksVisibilityClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(text = "Мои дела")
                    Text(
                        text = "Выполнено: $completedTasksCount",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                AnimatedVisibility(
                    visible = dataIsActual == false,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onSyncClick) {
                            Icon(
                                imageVector = Icons.Rounded.Refresh,
                                contentDescription = "Обновить",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                        Text(
                            text = "Данные устарели",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        },
        actions = {
            IconButton(onClick = onChangeCompletedTasksVisibilityClick) {
                if (showCompletedTasks) {
                    Icon(
                        painter = painterResource(id = UiR.drawable.ic_invisible_24),
                        contentDescription = "Скрыть выполненные задачи"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = UiR.drawable.ic_visible_24),
                        contentDescription = "Показать выполненные задачи"
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@Composable
private fun ScreenLoading() {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
private fun ScreenView(
    items: List<TodoItem>,
    onItemCheckBoxClick: (id: String, done: Boolean) -> Unit,
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
    ) {
        if (items.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }
        items(items) { item ->
            TodoListItem(
                item = item,
                onClick = { onItemClick(item.id) },
                onCheckedChange = { done -> onItemCheckBoxClick(item.id, done) },
            )
        }
        if (items.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }
    }
}

@Composable
private fun ScreenEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(2f))
        Text(
            text = "Список пуст",
            style = MaterialTheme.typography.displaySmall,
        )
        Spacer(Modifier.height(8.dp))
        Text(text = "Добавляйте элементы в ваш список дел")
        Spacer(Modifier.weight(3f))
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        Screen()
    }
}

@PreviewLightDark
@Composable
private fun Preview1() {
    AppTheme {
        Screen(
            showCompletedTasks = true,
            completedTasksCount = 5,
            screenState = VIEW,
            items = listOf(
                TodoItem(
                    id = "1",
                    text = "Задача 1",
                    importance = Importance.LOW,
                    deadline = null,
                    done = false,
                    createdAt = Date(),
                    changedAt = Date(),
                ),
                TodoItem(
                    id = "2",
                    text = "Задача 2",
                    importance = Importance.MEDIUM,
                    deadline = null,
                    done = true,
                    createdAt = Date(),
                    changedAt = Date(),
                ),
                TodoItem(
                    id = "3",
                    text = "Задача 3",
                    importance = Importance.HIGH,
                    deadline = null,
                    done = false,
                    createdAt = Date(),
                    changedAt = Date(),
                ),
            ),
        )
    }
}