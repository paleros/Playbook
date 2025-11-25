package com.peros.playbook

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.peros.playbook.features.game.data.DatabaseDriverFactory
import com.peros.playbook.core.data.GameAPIRemoteRepository
import com.peros.playbook.features.game.data.local.GameLocalRepository
import com.peros.playbook.features.game.domain.GameUseCases
import com.peros.playbook.core.ui.theme.AppTheme

/**
 * A fo fuggveny, ami elinditja a Compose Desktop alkalmazast
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Playbook",
    ) {
        AppTheme {
            App {
                val repository = GameLocalRepository(DatabaseDriverFactory().createDriver())
                val remoteRepository = GameAPIRemoteRepository()
                GameUseCases(repository, remoteRepository)
            }
        }
    }
}