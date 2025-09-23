package com.peros.playbook

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.peros.playbook.game.Game
import com.peros.playbook.presentation.home.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        // TEST
        MainScreen(games = List(10) { Game() },
            onMenuClick = {},
            onFilterClick = {},
            onSortClick = {},
            onRandomClick = {},
            onGameClick = {}
        )
    }

}