package com.example.todoapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,
    primaryContainer = LightPrimary,
    onPrimaryContainer = White,
    surface = LightSurface,
    onSurface = Color.Black,
    background = LightSurface,
    surfaceContainer = LightSurfaceContainer,
    onBackground = Color.Black,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = Color.Black,
    outline = LightGreyForText,
    surfaceContainerLow = LightSurfaceContainer,
    surfaceContainerLowest = LightGreyForText,
    surfaceContainerHighest = LightSurfaceVariant,
    error = LightRejectRed,
    onError = Color.White,
    errorContainer = LightRejectRed,
    onErrorContainer = White
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color.Black,
    primaryContainer = DarkPrimary,
    onPrimaryContainer = White,
    surface = DarkSurface,
    surfaceContainer = DarkSurfaceContainer,
    onSurface = Color.White,
    background = DarkSurface,
    onBackground = Color.White,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color.White,
    outline = DarkGreyForText,
    surfaceContainerLow = DarkSurfaceContainer,
    surfaceContainerLowest = DarkGreyForText,
    surfaceContainerHighest = DarkSurfaceVariant,
    error = DarkRejectRed,
    onError = Color.Black,
    errorContainer = DarkRejectRed,
    onErrorContainer = White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}