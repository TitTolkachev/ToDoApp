package com.example.todoapp.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.core.designsystem.theme.AppTheme
import com.example.todoapp.core.model.AppTheme
import com.example.todoapp.feature.settings.components.TopBar

@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
) {
    val viewModel: SettingsViewModel = hiltViewModel()

    Screen(
        selectedTheme = viewModel.theme.collectAsStateWithLifecycle().value,
        onBackClick = navigateBack,
        onThemeSelected = viewModel::selectTheme
    )
}

@Composable
private fun Screen(
    selectedTheme: AppTheme? = AppTheme.SYSTEM,
    onBackClick: () -> Unit = {},
    onThemeSelected: (AppTheme) -> Unit = {},
) {
    Scaffold(
        topBar = { TopBar(onBackClick) }
    ) { paddingValues ->
        Content(
            modifier = Modifier.padding(paddingValues),
            selectedTheme = selectedTheme,
            onThemeSelected = onThemeSelected
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    selectedTheme: AppTheme? = AppTheme.SYSTEM,
    onThemeSelected: (AppTheme) -> Unit = {},
) {
    val options = remember { AppTheme.entries.toTypedArray() }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.select_theme))

        SingleChoiceSegmentedButtonRow(Modifier.padding(16.dp)) {
            options.forEachIndexed { index, value ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    onClick = { onThemeSelected(options[index]) },
                    selected = index == selectedTheme?.ordinal,
                ) {
                    Text(
                        text = when (value) {
                            AppTheme.LIGHT -> stringResource(id = R.string.light_theme)
                            AppTheme.DARK -> stringResource(id = R.string.dark_theme)
                            AppTheme.SYSTEM -> stringResource(id = R.string.system_theme)
                        }
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Screen()
        }
    }
}