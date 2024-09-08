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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.todoapp.core.designsystem.theme.AppTheme
import com.example.todoapp.core.model.Importance
import com.example.todoapp.core.model.Importance.HIGH
import com.example.todoapp.core.model.Importance.LOW
import com.example.todoapp.core.model.Importance.MEDIUM
import com.example.todoapp.feature.todo.R

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
                    text = stringResource(R.string.todo_item_importance_medium),
                    contentDescription = stringResource(R.string.todo_item_importance_bs_medium_description),
                    onClickLabel = stringResource(R.string.todo_item_apply_importance_description),
                    onClick = { onImportanceChange(MEDIUM); onHide() }
                )
                HorizontalDivider()
                Item(
                    text = stringResource(R.string.todo_item_importance_low),
                    contentDescription = stringResource(R.string.todo_item_importance_bs_low_description),
                    onClickLabel = stringResource(R.string.todo_item_apply_importance_description),
                    onClick = { onImportanceChange(LOW); onHide() }
                )
                HorizontalDivider()
                Item(
                    text = stringResource(R.string.todo_item_importance_high),
                    contentDescription = stringResource(R.string.todo_item_importance_bs_high_description),
                    onClickLabel = stringResource(R.string.todo_item_apply_importance_description),
                    onClick = { onImportanceChange(HIGH); onHide() }
                )
            }
        }
    }
}

@Composable
private fun Item(
    text: String,
    contentDescription: String? = null,
    onClickLabel: String? = null,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick, onClickLabel = onClickLabel)
            .padding(12.dp)
            .semantics { this.contentDescription = contentDescription ?: text },
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