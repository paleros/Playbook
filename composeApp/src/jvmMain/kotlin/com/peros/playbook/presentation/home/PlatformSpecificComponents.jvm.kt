package com.peros.playbook.presentation.home

import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.add_new_game

/**
 * A csak a JVM-hez tartozo menuelemek
 */
@Composable
actual fun PlatformSpecificDrawerItems( onClick: () -> Unit) {
    NavigationDrawerItem(
        label = { Text(stringResource(Res.string.add_new_game)) },
        selected = false,
        onClick = { onClick() },
    )
}