package com.example.todoapp.presentation.screen.todoitem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.example.todoapp.presentation.screen.todoitem.TodoItemViewModel.Companion.Factory
import com.example.todoapp.presentation.theme.AppTheme

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
            Card {
                TextField(value = "", onValueChange = {})
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