package com.peros.playbook.database

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.no_internet_connection
import playbook.composeapp.generated.resources.ok

/**
 * Ellenorzi, hogy van-e elerheto halozat
 * @return true, ha van elerheto halozat, kulonben false
 */
expect fun isNetworkAvailable(): Boolean

/**
 * Nincs internetkapcsolat esetere megjeleno figyelmezteto dialogus
 * @param text a dialogus szovege
 * @param onDismiss a dialogus elvetese esemeny
 * @param showDialog a dialogus megjelenitesenek allapota
 */
@Composable
fun NoInternetAlertDialog(
    text: String,
    onDismiss: () -> Unit,
    showDialog: Boolean
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(stringResource(Res.string.no_internet_connection)) },
            text = { Text(text) },
            confirmButton = {
                Button(onClick = { onDismiss() }) {
                    Text(stringResource(Res.string.ok))
                }
            }
        )
    }
}