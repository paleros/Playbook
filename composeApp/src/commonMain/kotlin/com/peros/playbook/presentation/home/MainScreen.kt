package com.peros.playbook.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.peros.playbook.game.Game
import com.peros.playbook.presentation.game.GameCard
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A fo kepernyo, ami a jatekok listajat jeleniti meg
 * @param games a jatekok listaja
 * @param onMenuClick a menugomb kattintas esemeny
 * @param onFilterClick a szurogomb kattintas esemeny
 * @param onSortClick a rendezogomb kattintas esemeny
 * @param onRandomClick a random gomb kattintas esemeny
 * @param onGameClick a jatek elem kattintas esemeny
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    games: List<Game>,
    onMenuClick: () -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit,
    onRandomClick: () -> Unit,
    onGameClick: (Game) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar { onMenuClick }
            // TODO menu
        },
        bottomBar = {
            BottomBar(
                onSortClick = onSortClick,
                onFilterClick = onFilterClick,
                onRandomClick = onRandomClick
                //TODO szuro, rendez, random
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(games) { game ->
                GameCard(
                    game = game,
                    onClick = { onGameClick(game) }
                    //TODO jatek reszletei ablak
                )
            }
        }
    }
}

/**
 * Preview a MainScreen komponenshez
 */
@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        games = List(10) { Game() },
        onMenuClick = {},
        onFilterClick = {},
        onSortClick = {},
        onRandomClick = {},
        onGameClick = {}
    )
}