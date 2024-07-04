package com.example.todoapp.presentation.screen.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.App
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoItemsRepository
import com.example.todoapp.presentation.screen.todolist.model.TodoListScreenState.EMPTY
import com.example.todoapp.presentation.screen.todolist.model.TodoListScreenState.LOADING
import com.example.todoapp.presentation.screen.todolist.model.TodoListScreenState.VIEW
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val todoItemsRepository: TodoItemsRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            _showSnackbar.emit(exception.message ?: "Unknown error")
        }
    }

    private val _screenState = MutableStateFlow(LOADING)
    val screenState = _screenState.asStateFlow()

    private val _showCompletedTasks = MutableStateFlow(true)
    val showCompletedTasks = _showCompletedTasks.asStateFlow()

    /** Все элементы. */
    private val _allItems = todoItemsRepository.items.onStart { delay(1000L) }

    /** Элементы, показываемые на экране. */
    private val _items: MutableStateFlow<List<TodoItem>?> = MutableStateFlow(null)
    val items = _items.asStateFlow()

    private val _completedTasksCount = MutableStateFlow(0)
    val completedTasksCount = _completedTasksCount.asStateFlow()

    private val _showSnackbar = MutableSharedFlow<String>()
    val showSnackbar = _showSnackbar.asSharedFlow()

    init {
        listenToTaskList()
    }

    fun changeCompletedTasksVisibility() {
        _showCompletedTasks.update { !it }
    }

    fun changeItemCompletionStatus(
        itemId: String,
        completed: Boolean
    ) = viewModelScope.launch(Dispatchers.Default + exceptionHandler) {
        val item = _items.value?.firstOrNull { it.id == itemId } ?: return@launch
        todoItemsRepository.updateItem(item.copy(done = completed))
    }

    private fun listenToTaskList() = combine(
        _showCompletedTasks,
        _allItems,
    ) { showCompletedTasks, list ->
        val itemsToShow = list.filter { showCompletedTasks || !it.done }
        _items.update { itemsToShow }
        _completedTasksCount.update { list.count { it.done } }
        _screenState.update { if (itemsToShow.isNotEmpty()) VIEW else EMPTY }
    }.flowOn(Dispatchers.Default + exceptionHandler).launchIn(viewModelScope)

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