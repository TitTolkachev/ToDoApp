package com.example.todoapp.feature.todo.todolist.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.selectableGroup
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.todoapp.core.designsystem.common.noRippleClickable
import com.example.todoapp.core.designsystem.theme.AppTheme
import com.example.todoapp.core.model.Importance.HIGH
import com.example.todoapp.core.model.Importance.LOW
import com.example.todoapp.core.model.Importance.MEDIUM
import com.example.todoapp.core.model.TodoItem
import com.example.todoapp.feature.todo.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.todoapp.core.designsystem.R as UiR

@Composable
fun TodoListItem(
    item: TodoItem,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(top = 12.dp, end = 12.dp, bottom = 12.dp, start = 4.dp)
            .accessibilityDescription(
                item = item,
                onClick = onClick,
                onCheckedChange = onCheckedChange
            )
    ) {
        Checkbox(
            checked = item.done,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .offset(y = (-12).dp)
                .clearAndSetSemantics { },
        )

        Spacer(Modifier.width(4.dp))

        when (item.importance) {
            LOW -> {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = UiR.drawable.ic_low_importance),
                    contentDescription = null,
                )
                Spacer(Modifier.width(4.dp))
            }

            MEDIUM -> {}
            HIGH -> {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = UiR.drawable.ic_high_importance),
                    contentDescription = null,
                )
                Spacer(Modifier.width(4.dp))
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .clearAndSetSemantics { }
                .noRippleClickable(onClick = onClick)
                .clearAndSetSemantics { }
        ) {
            if (item.done) {
                CrossedText(text = item.text)
            } else {
                BasicText(text = item.text)
            }

            Spacer(Modifier.height(2.dp))

            item.deadline?.let {
                Deadline(it)
            }

        }

        Spacer(Modifier.width(12.dp))
    }
}

@Composable
private fun BasicText(
    text: String,
) {
    Text(
        modifier = Modifier.clearAndSetSemantics { },
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun CrossedText(
    text: String,
) {
    Text(
        modifier = Modifier.clearAndSetSemantics { },
        text = text,
        color = MaterialTheme.colorScheme.outline,
        style = MaterialTheme.typography.bodyLarge.copy(
            textDecoration = TextDecoration.LineThrough
        ),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun Deadline(date: Date) {
    val formatter = remember {
        SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    }
    Text(
        modifier = Modifier.clearAndSetSemantics { },
        text = formatter.format(date),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@SuppressLint("ComposableNaming")
@Composable
private fun Modifier.accessibilityDescription(
    item: TodoItem,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
): Modifier {
    val resources = LocalContext.current.resources
    val formatter = remember {
        SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    }

    return this.semantics(mergeDescendants = true) {
        selectableGroup()
        selected = item.done

        customActions = listOf(
            CustomAccessibilityAction(
                label = resources.getString(
                    if (item.done)
                        R.string.todo_list_mark_item_not_done
                    else
                        R.string.todo_list_mark_item_done
                )
            ) {
                onCheckedChange(!item.done)
                true
            },
            CustomAccessibilityAction(resources.getString(R.string.todo_list_show_item)) {
                onClick()
                true
            }
        )

        contentDescription = resources.getString(
            when (item.importance) {
                LOW -> R.string.todo_list_item_importance_low
                MEDIUM -> R.string.todo_list_item_importance_medium
                HIGH -> R.string.todo_list_item_importance_high
            }
        ) + item.text + (item.deadline?.let {
            ". ${
                resources.getString(
                    R.string.todo_list_item_deadline_description,
                    formatter.format(it)
                )
            }"
        } ?: "")
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        LazyColumn {
            item {
                TodoListItem(
                    item = TodoItem(
                        id = "1",
                        text = "Buy groceries",
                        importance = MEDIUM,
                        deadline = Date(Date().time - 86400000),
                        done = false,
                        createdAt = Date(Date().time + 86400000),
                        changedAt = null
                    ),
                    onCheckedChange = {},
                ) {}
            }
        }
    }
}
