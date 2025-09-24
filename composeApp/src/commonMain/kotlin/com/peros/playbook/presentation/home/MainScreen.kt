package com.peros.playbook.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.peros.playbook.game.Game
import com.peros.playbook.presentation.game.GameCard
import com.peros.playbook.presentation.game.GameDetailsDialog
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A fo kepernyo, ami a jatekok listajat jeleniti meg
 * @param games a jatekok listaja
 * @param onMenuClick a menugomb kattintas esemeny
 * @param onFilterClick a szurogomb kattintas esemeny
 * @param onSortClick a rendezogomb kattintas esemeny
 * @param onRandomClick a random gomb kattintas esemeny
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    games: List<Game>,
    onMenuClick: () -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit,
    onRandomClick: () -> Unit
) {
    var selectedGame by remember { mutableStateOf<Game?>(null) }

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
                    onClick = { selectedGame = game }
                )
            }
        }
    }

    if (selectedGame != null) {
        GameDetailsDialog(
            game = selectedGame!!,
            onDismiss = { selectedGame = null }
        )
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
        onSortClick = {}
    ) {}
}