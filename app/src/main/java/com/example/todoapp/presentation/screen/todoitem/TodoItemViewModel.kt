package com.example.todoapp.presentation.screen.todoitem

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.App
import com.example.todoapp.data.model.Importance
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoItemsRepository
import kotlinx.coroutines.launch
import java.util.Date

class TodoItemViewModel(
    private val todoItemsRepository: TodoItemsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val todoItemId: String? = savedStateHandle["todo_item_id"]

    private val text: String? = if (todoItemId == null) null else ""
    private val importance: Importance? = if (todoItemId == null) null else Importance.MEDIUM
    private val deadline: String? = if (todoItemId == null) null else ""

    fun saveItem() = viewModelScope.launch {
        val item = TodoItem(
            id = "",
            text = "TextTextText",
            importance = Importance.HIGH,
            deadline = null,
            done = false,
            creationDate = Date(),
            updateDate = null,
        )

        if (todoItemId == null) {
            todoItemsRepository.createItem(item)
        } else {
            todoItemsRepository.updateItem(item.copy(id = todoItemId))
        }
    }

    fun deleteItem() = viewModelScope.launch {
        todoItemId?.let {
            todoItemsRepository.deleteItem(todoItemId)
        }
    }

    companion object {
        fun Factory(savedStateHandle: SavedStateHandle): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
                val repository = application.container.todoItemsRepository
                TodoItemViewModel(repository, savedStateHandle)
            }
        }
    }
}