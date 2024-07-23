package com.example.todoapp.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = White,
    primaryContainer = LightPrimary,
    onPrimaryContainer = White,
    surface = LightSurface,
    onSurface = Black,
    background = LightSurface,
    surfaceContainer = LightSurfaceContainer,
    onBackground = Black,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = Black,
    outline = LightGreyForText,
    surfaceContainerLow = LightSurfaceContainer,
    surfaceContainerLowest = LightGreyForText,
    surfaceContainerHighest = LightSurfaceVariant,
    error = LightRejectRed,
    onError = White,
    errorContainer = LightRejectRed,
    onErrorContainer = White
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Black,
    primaryContainer = DarkPrimary,
    onPrimaryContainer = White,
    surface = DarkSurface,
    surfaceContainer = DarkSurfaceContainer,
    onSurface = White,
    background = DarkSurface,
    onBackground = White,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = White,
    outline = DarkGreyForText,
    surfaceContainerLow = DarkSurfaceContainer,
    surfaceContainerLowest = DarkGreyForText,
    surfaceContainerHighest = DarkSurfaceVariant,
    error = DarkRejectRed,
    onError = Black,
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as Activity
            WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(activity.window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}