package com.example.todoapp.feature.todo.todolist.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.selectableGroup
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import com.example.todoapp.feature.todo.R
import com.example.todoapp.core.designsystem.R as UiR

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    showCompletedTasks: Boolean,
    completedTasksCount: Int,
    dataIsActual: Boolean? = true,
    onSyncClick: () -> Unit,
    onChangeCompletedTasksVisibilityClick: () -> Unit,
    onInfoIconClick: () -> Unit = {},
    onSettingsIconClick: () -> Unit = {},
) {
    val resources = LocalContext.current.resources
    TopAppBar(
        title = { Title(completedTasksCount, dataIsActual, onSyncClick) },
        actions = {
            IconButton(onClick = onInfoIconClick) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = stringResource(R.string.todo_list_top_bar_app_info_description)
                )
            }
            IconButton(
                modifier = Modifier.semantics(mergeDescendants = true) {
                    selectableGroup()
                    selected = showCompletedTasks

                    contentDescription = resources.getString(
                        R.string.todo_list_top_bar_show_completed_description
                    )
                },
                onClick = onChangeCompletedTasksVisibilityClick
            ) {
                if (showCompletedTasks) {
                    Icon(
                        modifier = Modifier.clearAndSetSemantics { },
                        painter = painterResource(id = UiR.drawable.ic_invisible_24),
                        contentDescription = null
                    )
                } else {
                    Icon(
                        modifier = Modifier.clearAndSetSemantics { },
                        painter = painterResource(id = UiR.drawable.ic_visible_24),
                        contentDescription = null
                    )
                }
            }
            IconButton(onClick = onSettingsIconClick) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(R.string.todo_list_top_bar_settings_description)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun Title(
    completedTasksCount: Int,
    dataIsActual: Boolean?,
    onSyncClick: () -> Unit
) {
    val resources = LocalContext.current.resources
    Row(
        modifier = Modifier.semantics(mergeDescendants = true) {
            heading()
            liveRegion = LiveRegionMode.Assertive

            contentDescription = resources.getString(
                R.string.todo_list_top_bar_done_description,
                completedTasksCount
            ) + if (dataIsActual == false) {
                resources.getString(R.string.todo_list_top_bar_old_data_description)
            } else ""

            customActions = if (dataIsActual == false) listOf(
                CustomAccessibilityAction(resources.getString(R.string.todo_list_top_bar_refresh_description)) {
                    onSyncClick()
                    true
                }
            ) else emptyList()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                modifier = Modifier.clearAndSetSemantics { },
                text = stringResource(R.string.todo_list_top_bar_title)
            )
            Text(
                modifier = Modifier.clearAndSetSemantics { },
                text = stringResource(R.string.todo_list_top_bar_done, completedTasksCount),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        RefreshDataBlock(dataIsActual, onSyncClick)
    }
}

@Composable
private fun RefreshDataBlock(
    dataIsActual: Boolean?,
    onSyncClick: () -> Unit
) {
    AnimatedVisibility(
        visible = dataIsActual == false,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                modifier = Modifier.clearAndSetSemantics { },
                onClick = onSyncClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
            Text(
                modifier = Modifier.clearAndSetSemantics { },
                text = stringResource(R.string.todo_list_top_bar_old_data),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}