package com.example.todoapp.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@PreviewLightDark
@Composable
private fun ColorThemeLightPreview() {
    AppTheme {
        Surface {
            FlowRow(
                modifier = Modifier.padding(32.dp),
                verticalArrangement = spacedBy(4.dp),
                horizontalArrangement = spacedBy(4.dp),
                maxItemsInEachRow = 3,
            ) {
                Item(
                    MaterialTheme.colorScheme.primary,
                    "Primary"
                )

                Item(
                    MaterialTheme.colorScheme.onPrimary,
                    "onPrimary"
                )

                Item(
                    MaterialTheme.colorScheme.primaryContainer,
                    "primaryContainer"
                )

                Item(
                    MaterialTheme.colorScheme.onPrimaryContainer,
                    "onPrimaryContainer",
                    Color.Gray
                )

                Item(
                    MaterialTheme.colorScheme.surface,
                    "surface"
                )

                Item(
                    MaterialTheme.colorScheme.onSurface,
                    "onSurface",
                    Color.Gray
                )

                Item(
                    MaterialTheme.colorScheme.background,
                    "background"
                )

                Item(
                    MaterialTheme.colorScheme.onBackground,
                    "onBackground",
                    Color.Gray
                )

                Item(
                    MaterialTheme.colorScheme.surfaceContainer,
                    "background"
                )

                Item(
                    MaterialTheme.colorScheme.surfaceVariant,
                    "surfaceVariant"
                )

                Item(
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    "onSurfaceVariant",
                    Color.Gray
                )

                Item(
                    MaterialTheme.colorScheme.outline,
                    "outline"
                )

                Item(
                    MaterialTheme.colorScheme.surfaceContainerLow,
                    "surfaceContainerLow"
                )

                Item(
                    MaterialTheme.colorScheme.surfaceContainerLowest,
                    "surfaceContainerLowest"
                )

                Item(
                    MaterialTheme.colorScheme.error,
                    "error"
                )

                Item(
                    MaterialTheme.colorScheme.onError,
                    "onError"
                )

                Item(
                    MaterialTheme.colorScheme.errorContainer,
                    "errorContainer"
                )

                Item(
                    MaterialTheme.colorScheme.onErrorContainer,
                    "onErrorContainer",
                    textColor = Color.Red
                )
            }
        }
    }
}

@Composable
private fun Item(
    color: Color,
    text: String,
    textColor: Color = contentColorFor(color),
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .border(1.dp, textColor, RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}