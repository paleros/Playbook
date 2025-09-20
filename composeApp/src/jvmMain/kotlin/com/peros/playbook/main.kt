package com.peros.playbook

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.peros.playbook.theme.AppTheme

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Playbook",
    ) {
        AppTheme {
            App()
        }
    }
}