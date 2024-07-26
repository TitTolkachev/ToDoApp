package com.example.todoapp.feature.todo.todoitem.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.selectableGroup
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import com.example.todoapp.feature.todo.R

@Composable
internal fun DeadlineBlock(
    deadline: String?,
    datePickerVisible: Boolean,
    onDeadlineChange: (deadline: Long?) -> Unit,
    showDatePicker: () -> Unit = {},
) {
    Row(
        Modifier.accessibilityDescription(
            deadline = deadline,
            onDeadlineChange = onDeadlineChange,
            showDatePicker = showDatePicker
        )
    ) {
        Column {
            Text(
                modifier = Modifier.clearAndSetSemantics { },
                text = stringResource(R.string.todo_item_deadline),
                style = MaterialTheme.typography.bodyLarge,
            )
            if (!deadline.isNullOrBlank()) {
                Text(
                    modifier = Modifier.clearAndSetSemantics { },
                    text = deadline,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Switch(
            modifier = Modifier.clearAndSetSemantics { },
            checked = !deadline.isNullOrBlank() || datePickerVisible,
            onCheckedChange = {
                if (it) showDatePicker()
                else onDeadlineChange(null)
            }
        )
    }
}

@SuppressLint("ComposableNaming")
@Composable
private fun Modifier.accessibilityDescription(
    deadline: String?,
    onDeadlineChange: (deadline: Long?) -> Unit,
    showDatePicker: () -> Unit,
): Modifier {
    val resources = LocalContext.current.resources
    return this.semantics(mergeDescendants = true) {
        selectableGroup()
        selected = !deadline.isNullOrBlank()

        customActions = listOf(
            CustomAccessibilityAction(
                label = if (deadline.isNullOrBlank()) {
                    resources.getString(R.string.todo_item_select_deadline_description)
                } else {
                    resources.getString(R.string.todo_item_cancel_deadline_description)
                },
                action = {
                    if (deadline.isNullOrBlank()) {
                        showDatePicker()
                    } else {
                        onDeadlineChange(null)
                    }
                    true
                }
            )
        )

        contentDescription =
            resources.getString(R.string.todo_item_deadline_description) + (deadline ?: "")
    }
}