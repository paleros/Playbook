package com.peros.playbook.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.peros.playbook.game.Game
import com.peros.playbook.presentation.game.GameCard
import com.peros.playbook.presentation.game.GameDetailsDialog
import com.peros.playbook.presentation.menu.FilterDialog
import com.peros.playbook.presentation.menu.FilterState
import com.peros.playbook.presentation.ui.FireworksEffect
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A fo kepernyo, ami a jatekok listajat jeleniti meg
 * @param games a jatekok listaja
 * @param onMenuClick a menugomb kattintas esemeny
 * @param onFilterClick a szurogomb kattintas esemeny
 */
@Suppress("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    games: List<Game>,
    onMenuClick: () -> Unit,
    onFilterClick: () -> Unit,
) {
    var showFilterDialog by remember { mutableStateOf(false) }
    var showRandomGameDialog by remember { mutableStateOf(false) }
    var selectedGame by remember { mutableStateOf<Game?>(null) }
    var sortState by remember { mutableStateOf(SORTSTATE.NAMEASC) }

    var filterState by remember { mutableStateOf(
        FilterState(
            players = setOf(),
            time = setOf(),
            age = setOf(),
            location = setOf(),
            noSupplies = false,
            onlyFavorites = false)
    ) }

    /** Szure es rendezes */
    val filteredAndSortedGames by remember(games, filterState, sortState) {
        derivedStateOf {
            games
                .filter { game ->
                    (filterState.players.isEmpty() || filterState.players.any { it in game.numberOfPlayers }) &&
                            (filterState.time.isEmpty() || filterState.time.any { it in game.time }) &&
                            (filterState.age.isEmpty() || filterState.age.any { it in game.ageGroup }) &&
                            (filterState.location.isEmpty() || filterState.location.any { it in game.location }) &&
                            (!filterState.noSupplies || game.supplies.isEmpty()) &&
                            (!filterState.onlyFavorites || game.liked)
                }
                .sortedWith { game1, game2 ->
                    when (sortState) {
                        SORTSTATE.NAMEASC -> game1.name.compareTo(game2.name)
                        SORTSTATE.NAMEDESC -> game2.name.compareTo(game1.name)
                    }
                }
        }
    }

    Scaffold(
        topBar = {
            TopBar { onMenuClick }
            // TODO menu
        },
        bottomBar = {
            BottomBar(
                onSortClick = {
                    sortState = if (sortState == SORTSTATE.NAMEASC) SORTSTATE.NAMEDESC
                    else SORTSTATE.NAMEASC
                              },
                onFilterClick = { showFilterDialog = true
                                },
                onRandomClick = {
                    showRandomGameDialog = true}
                //TODO random
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(items = filteredAndSortedGames,
                key = { game -> game.name }
            ) { game ->
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
            onDismiss = { selectedGame = null
                            filterState = filterState.copy()} //TODO kedveles megjelenes hiba javitasa (details szivecske utan nem jelenik meg a listan)
        )
    }

    if (showRandomGameDialog) {
        RandomGameDetailsDialog(selectedGames = filteredAndSortedGames,
            onDismiss = { showRandomGameDialog = false })
    }

    if (showFilterDialog) {
        FilterDialog(
            filterState = filterState,
            onDismiss = {showFilterDialog = false},
            onApply = {newFilterState ->
                filterState = newFilterState
                showFilterDialog = false} )
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
    )
}