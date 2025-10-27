package com.peros.playbook

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.peros.playbook.database.GameUseCases
import com.peros.playbook.game.Game
import com.peros.playbook.presentation.home.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A fomenupont komponens, amely az alkalmazast jeleniti meg
 * @param gameUseCasesProvider Egy fuggveny, ami a jatek hasznalat eseteit szolgaltatja
 */
@Suppress("CoroutineCreationDuringComposition", "UnrememberedMutableState")
@Composable
@Preview
fun App(
    gameUseCasesProvider: () -> GameUseCases,
) {
    var isInitialized by remember { mutableStateOf(false) }
    var games by remember { mutableStateOf<List<Game>>(emptyList()) }
    var gameUseCases by remember { mutableStateOf<GameUseCases?>(null) }

    LaunchedEffect(Unit) {
        gameUseCases = gameUseCasesProvider()
        games = gameUseCases!!.getAllGames()
        isInitialized = true
    }

    MaterialTheme {
        if (!isInitialized) {
            SplashScreen()
        } else {
            MainScreen(
                gameList = mutableStateOf(games),
                gameUseCases = gameUseCases!!,
            )
        }
    }
}