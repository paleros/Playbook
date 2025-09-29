package com.peros.playbook

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.peros.playbook.database.GameUseCases
import com.peros.playbook.presentation.home.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(gameUseCases: GameUseCases) {
    MaterialTheme {
        //TODO ezek csak TEST jatekok
        generateTestGames(gameUseCases)

        val games = remember { mutableStateOf(gameUseCases.getAllGames()) }
        MainScreen(
            gameList = games,
            onMenuClick = {},
            onFilterClick = {},
        )
    }

}