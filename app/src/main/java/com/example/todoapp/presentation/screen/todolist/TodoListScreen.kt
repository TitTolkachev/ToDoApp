package com.example.todoapp.presentation.screen.todolist

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.R
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.presentation.screen.todolist.components.TodoListItem
import com.example.todoapp.presentation.screen.todolist.model.TodoListScreenState
import com.example.todoapp.presentation.theme.AppTheme

@Composable
fun TodoListScreen(
    navigateToItem: (todoItemId: String) -> Unit,
    navigateToItemCreate: () -> Unit,
) {
    val viewModel: TodoListViewModel = viewModel(factory = TodoListViewModel.Factory)

    Screen(
        items = viewModel.items.collectAsState().value ?: emptyList(),
        showCompletedTasks = viewModel.showCompletedTasks.collectAsState().value,
        screenState = viewModel.screenState.collectAsState().value,

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
    screenState: TodoListScreenState = TodoListScreenState.EMPTY,

    onItemClick: (String) -> Unit = {},
    onItemCheckBoxClick: (id: String, done: Boolean) -> Unit = { _, _ -> },
    onFabClick: () -> Unit = {},
    onChangeCompletedTasksVisibilityClick: () -> Unit = {},
) {
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = "Мои дела") },
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
                TodoListScreenState.LOADING -> {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }

                TodoListScreenState.VIEW -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                    ) {
                        if (items.isNotEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                )
                            }
                        }
                        items(items) { item ->
                            TodoListItem(
                                item = item,
                                onCheckedChange = { done ->
                                    onItemCheckBoxClick(item.id, done)
                                },
                            ) { onItemClick(item.id) }
                        }
                        if (items.isNotEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                bottomStart = 8.dp,
                                                bottomEnd = 8.dp
                                            )
                                        )
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                )
                            }
                        }
                    }
                }

                TodoListScreenState.EMPTY -> {
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