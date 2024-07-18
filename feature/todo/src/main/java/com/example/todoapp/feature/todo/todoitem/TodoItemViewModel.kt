package com.example.todoapp.feature.todo.todoitem

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.core.model.Importance
import com.example.todoapp.feature.todo.todoitem.model.TodoItemScreenMode
import com.example.todoapp.feature.todo.todoitem.model.TodoItemScreenMode.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TodoItemViewModel @Inject constructor(
    private val todoItemsRepository: com.example.todoapp.core.data.TodoItemsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val todoItemId: String? = savedStateHandle[TODO_ITEM_ID]
    val mode: TodoItemScreenMode = if (todoItemId == null) CREATE else EDIT

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            _showSnackbar.emit(exception.message ?: "Unknown error")
        }
    }

    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()
    private val _importance = MutableStateFlow(Importance.MEDIUM)
    val importance = _importance.asStateFlow()
    private val _deadline = MutableStateFlow("")
    val deadline = _deadline.asStateFlow()

    private val _navigateBack = MutableSharedFlow<Boolean>()
    val navigateBack = _navigateBack.asSharedFlow()

    private val _showSnackbar = MutableSharedFlow<String>()
    val showSnackbar = _showSnackbar.asSharedFlow()

    private val formatter = SimpleDateFormat("d MMMM yyyy", java.util.Locale.getDefault())

    init {
        loadItemData()
    }

    fun onTextChange(text: String) {
        _text.update { text }
    }

    fun onImportanceChange(importance: Importance) {
        _importance.update { importance }
    }

    fun onDeadlineChange(deadline: Long?) {
        val date = deadline?.let { formatter.format(it) } ?: ""
        _deadline.update { date }
    }

    fun saveItem() = viewModelScope.launch(Dispatchers.Default + exceptionHandler) {
        val item = com.example.todoapp.core.model.TodoItem(
            id = "",
            text = _text.value,
            importance = _importance.value,
            deadline = try {
                formatter.parse(_deadline.value)
            } catch (_: Exception) {
                null
            },
            done = false,
            createdAt = Date(),
            changedAt = null,
        )

        if (mode == CREATE) {
            todoItemsRepository.createItem(item)
        } else {
            todoItemsRepository.updateItem(item.copy(id = todoItemId ?: return@launch))
        }

        _navigateBack.emit(true)
    }

    fun deleteItem() = viewModelScope.launch(Dispatchers.Default + exceptionHandler) {
        todoItemId?.let {
            todoItemsRepository.deleteItem(todoItemId)
            _navigateBack.emit(true)
        }
    }

    private fun loadItemData() {
        if (mode == CREATE) return
        viewModelScope.launch(Dispatchers.Default + exceptionHandler) {
            val item = todoItemsRepository.getItem(todoItemId ?: return@launch) ?: return@launch
            _text.update { item.text }
            _importance.update { item.importance }
            _deadline.update { item.deadline?.let { formatter.format(it) } ?: "" }
        }
    }

    companion object {
        private const val TODO_ITEM_ID = "todoItemId"
    }
}