package com.example.todoapp.feature.todo.todoitem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.todoapp.core.designsystem.theme.AppTheme
import com.example.todoapp.core.model.Importance
import com.example.todoapp.core.model.Importance.HIGH
import com.example.todoapp.core.model.Importance.LOW
import com.example.todoapp.core.model.Importance.MEDIUM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImportanceBottomSheet(
    visible: Boolean,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onImportanceChange: (Importance) -> Unit,
    onHide: () -> Unit,
) {
    if (visible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = CenterHorizontally,
            ) {
                Item(
                    text = "Нет",
                    onClick = { onImportanceChange(MEDIUM); onHide() }
                )
                HorizontalDivider()
                Item(
                    text = "Низкий",
                    onClick = { onImportanceChange(LOW); onHide() }
                )
                HorizontalDivider()
                Item(
                    text = "Высокий",
                    onClick = { onImportanceChange(HIGH); onHide() }
                )
            }
        }
    }
}

@Composable
private fun Item(
    text: String,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ImportanceBottomSheet(
                visible = true,
                sheetState = rememberStandardBottomSheetState(),
                onDismissRequest = {},
                onImportanceChange = {},
                onHide = {},
            )
        }
    }
}