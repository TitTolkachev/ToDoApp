package com.example.todoapp.core.designsystem.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.noRippleClickable(
    onClick: () -> Unit,
    onClickLabel: String? = null
): Modifier = composed {
    this.clickable(
        indication = null,
        onClickLabel = onClickLabel,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}