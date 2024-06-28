package com.example.todoapp.presentation.screen.todolist

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.R
import com.example.todoapp.data.model.Importance
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.presentation.screen.todolist.components.TodoListItem
import com.example.todoapp.presentation.screen.todolist.model.TodoListScreenState
import com.example.todoapp.presentation.theme.AppTheme
import java.util.Date

@Composable
fun TodoListScreen(
    navigateToItem: (todoItemId: String) -> Unit,
    navigateToItemCreate: () -> Unit,
) {
    val viewModel: TodoListViewModel = viewModel(factory = TodoListViewModel.Factory)
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
    screenState: TodoListScreenState = TodoListScreenState.EMPTY,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),

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
                TodoListScreenState.LOADING -> ScreenLoading()
                TodoListScreenState.VIEW -> ScreenView(items, onItemCheckBoxClick, onItemClick)
                TodoListScreenState.EMPTY -> ScreenEmpty()
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
    onChangeCompletedTasksVisibilityClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Column {
                Text(text = "Мои дела")
                Text(
                    text = "Выполнено: $completedTasksCount",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        actions = {
            IconButton(onClick = onChangeCompletedTasksVisibilityClick) {
                if (showCompletedTasks) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_invisible_24),
                        contentDescription = "Скрыть выполненные задачи"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_visible_24),
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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun Preview() {
    AppTheme {
        Screen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun Preview1() {
    AppTheme {
        Screen(
            showCompletedTasks = true,
            completedTasksCount = 5,
            screenState = TodoListScreenState.VIEW,
            items = listOf(
                TodoItem(
                    id = "1",
                    text = "Задача 1",
                    importance = Importance.LOW,
                    deadline = null,
                    done = false,
                    creationDate = Date(),
                    updateDate = Date(),
                ),
                TodoItem(
                    id = "2",
                    text = "Задача 2",
                    importance = Importance.MEDIUM,
                    deadline = null,
                    done = true,
                    creationDate = Date(),
                    updateDate = Date(),
                ),
                TodoItem(
                    id = "3",
                    text = "Задача 3",
                    importance = Importance.HIGH,
                    deadline = null,
                    done = false,
                    creationDate = Date(),
                    updateDate = Date(),
                ),
            ),
        )
    }
}