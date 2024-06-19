package com.example.todoapp.presentation.screen.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.App
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.presentation.screen.todolist.model.TodoListScreenState.EMPTY
import com.example.todoapp.presentation.screen.todolist.model.TodoListScreenState.LOADING
import com.example.todoapp.presentation.screen.todolist.model.TodoListScreenState.VIEW
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class TodoListViewModel(todoItemsRepository: TodoItemsRepository) : ViewModel() {

    private val _screenState = MutableStateFlow(LOADING)
    val screenState = _screenState.asStateFlow()

    val items = todoItemsRepository.items.onEach { list ->
        _screenState.update { if (list.isEmpty()) EMPTY else VIEW }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
                val repository = application.container.todoItemsRepository
                TodoListViewModel(repository)
            }
        }
    }
}