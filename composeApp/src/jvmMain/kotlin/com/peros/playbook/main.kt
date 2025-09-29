package com.peros.playbook

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.peros.playbook.database.DatabaseDriverFactory
import com.peros.playbook.database.GameRepository
import com.peros.playbook.database.GameUseCases
import com.peros.playbook.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.playbook

/**
 * A fo fuggveny, ami elinditja a Compose Desktop alkalmazast
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.playbook),
    ) {

        val repository = GameRepository(DatabaseDriverFactory().createDriver())
        val gameUseCases = GameUseCases(repository)

        AppTheme {
            App(gameUseCases)
        }
    }
}