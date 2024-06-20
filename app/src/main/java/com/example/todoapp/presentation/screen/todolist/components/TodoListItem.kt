package com.example.todoapp.presentation.screen.todolist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.model.Importance
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.presentation.common.noRippleClickable
import com.example.todoapp.presentation.theme.AppTheme
import java.util.Date

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
    ) {
        Checkbox(
            checked = item.done,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.offset(y = (-8).dp),
        )

        Spacer(Modifier.width(4.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onClick)
        ) {
            if (item.done) {
                CrossedText(text = item.text)
            } else {
                BasicText(text = item.text)
            }
        }

        Spacer(Modifier.width(12.dp))
    }
}

@Composable
fun BasicText(
    text: String,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun CrossedText(
    text: String,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.outline,
        style = MaterialTheme.typography.bodyLarge.copy(
            textDecoration = TextDecoration.LineThrough
        ),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        LazyColumn {
            item {
                TodoListItem(
                    item = TodoItem(
                        id = "1",
                        text = "Buy groceries",
                        importance = Importance.MEDIUM,
                        deadline = Date(Date().time - 86400000),
                        done = false,
                        creationDate = Date(Date().time + 86400000),
                        updateDate = null
                    ),
                    onCheckedChange = {},
                ) {}
            }
        }
    }
}
