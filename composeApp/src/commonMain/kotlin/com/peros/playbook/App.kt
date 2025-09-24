package com.peros.playbook

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.peros.playbook.presentation.home.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        // TEST
        val gameList = generateTestGames()
        MainScreen(
            games = gameList,
            onMenuClick = {},
            onFilterClick = {},
            onSortClick = {}
        ) {}
    }

}