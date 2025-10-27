package com.peros.playbook.presentation.home

import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.peros.playbook.database.isNetworkAvailable
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.add_new_game

/**
 * A csak a JVM-hez tartozo menuelemek
 * @param onClick a menuelemre kattintas esemeny
 * @param onNoInternet internetkapcsolat hianya esemeny
 * @param isNetworkAvailable jelzi, hogy van-e elerheto halozat
 */
@Composable
actual fun PlatformSpecificDrawerItems( onClick: () -> Unit, onNoInternet: () -> Unit, isNetworkAvailable: Boolean) {
    NavigationDrawerItem(
        label = { Text(stringResource(Res.string.add_new_game)) },
        selected = false,
        onClick = {
            if (!isNetworkAvailable()) {
            onNoInternet()
            return@NavigationDrawerItem
        }
            onClick() },
    )
}