package com.example.todoapp.presentation.screen.todolist.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.presentation.model.Importance.HIGH
import com.example.todoapp.presentation.model.Importance.LOW
import com.example.todoapp.presentation.model.Importance.MEDIUM
import com.example.todoapp.presentation.model.TodoItem
import com.example.todoapp.presentation.common.noRippleClickable
import com.example.todoapp.presentation.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            modifier = Modifier.offset(y = (-12).dp),
        )

        Spacer(Modifier.width(4.dp))

        when (item.importance) {
            LOW -> {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_low_importance),
                    contentDescription = "Низкая важность",
                )
                Spacer(Modifier.width(4.dp))
            }

            MEDIUM -> {}
            HIGH -> {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_high_importance),
                    contentDescription = "Высокая важность",
                )
                Spacer(Modifier.width(4.dp))
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onClick)
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
        text = formatter.format(date),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
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
                        importance = MEDIUM,
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
