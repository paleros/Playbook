package com.peros.playbook

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.peros.playbook.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.playbook
import playbook.composeapp.generated.resources.supplies

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.playbook),
    ) {
        AppTheme {
            App()
        }
    }
}