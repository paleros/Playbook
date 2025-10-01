package com.peros.playbook.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.peros.playbook.database.GameUseCases
import com.peros.playbook.game.Game
import com.peros.playbook.presentation.game.GameCard
import com.peros.playbook.presentation.game.GameDetailsDialog
import com.peros.playbook.presentation.menu.AboutDialog
import com.peros.playbook.presentation.menu.FilterDialog
import com.peros.playbook.presentation.menu.FilterState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.about
import playbook.composeapp.generated.resources.menu
import playbook.composeapp.generated.resources.problem_solving
import playbook.composeapp.generated.resources.update_games

/**
 * A fo kepernyo, ami a jatekok listajat jeleniti meg
 * @param gameList a jatekok listaja
 * @param gameUseCases a jatekokkal kapcsolatos use case-ek
 * @param onMenuClick a menugomb kattintas esemeny
 * @param onFilterClick a szurogomb kattintas esemeny
 */
@Suppress("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    gameList: MutableState<List<Game>>,
    gameUseCases: GameUseCases,
    onMenuClick: () -> Unit,
    onFilterClick: () -> Unit,
) {
    var showFilterDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showRandomGameDialog by remember { mutableStateOf(false) }
    var selectedGame by remember { mutableStateOf<Game?>(null) }
    var sortState by remember { mutableStateOf(SORTSTATE.NAMEASC) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    var games by remember { mutableStateOf(gameList.value) }

    var filterState by remember { mutableStateOf(
        FilterState(
            players = setOf(),
            time = setOf(),
            age = setOf(),
            location = setOf(),
            noSupplies = false,
            onlyFavorites = false)
    ) }
//TODO hibakezeles, ures lista eseten
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
                            (!filterState.onlyFavorites || game.liked) &&
                            (searchQuery.isBlank() || game.name.contains(searchQuery, ignoreCase = true))
                }
                .sortedWith { game1, game2 ->
                    when (sortState) {
                        SORTSTATE.NAMEASC -> game1.name.compareTo(game2.name)
                        SORTSTATE.NAMEDESC -> game2.name.compareTo(game1.name)
                    }
                }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primary,
                drawerContentColor = MaterialTheme.colorScheme.onPrimary,
                drawerTonalElevation = 8.dp
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.problem_solving),
                        contentDescription = "Logo",
                        modifier = Modifier.height(40.dp).width(40.dp)
                    )
                    Text(
                        stringResource(Res.string.menu),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.update_games)) },
                    selected = false,
                    onClick = {
                        isLoading = true
                        scope.launch { drawerState.close()}
                        CoroutineScope(Dispatchers.IO).launch {
                            gameUseCases.syncDown()

                            val updatedGames = gameUseCases.getAllGames()
                            withContext(Dispatchers.Main) {
                                games = updatedGames
                                isLoading = false
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.about)) },
                    selected = false,
                    onClick = { showAboutDialog = true }
                )
            }
        }
    ) {
        Scaffold(
            Modifier.background(MaterialTheme.colorScheme.background),
            topBar = {
                TopBar (onMenuClick = {
                    scope.launch { drawerState.open() }
                    },
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it }
                )
                // TODO menu (PC-hozzaadas, torles, stb.)
            },
            bottomBar = {
                BottomBar(
                    onSortClick = {
                        sortState = if (sortState == SORTSTATE.NAMEASC) SORTSTATE.NAMEDESC
                        else SORTSTATE.NAMEASC
                    },
                    onFilterClick = {
                        showFilterDialog = true
                    },
                    onRandomClick = {
                        showRandomGameDialog = true
                    }
                )
            }
        ) { paddingValues ->
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(
                        items = filteredAndSortedGames,
                    ) { game ->
                        GameCard(
                            game = game,
                            onClick = { selectedGame = game }
                        )
                    }
                }
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
    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }
}

/**
 * Preview a MainScreen komponenshez
 */
/*@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        games = List(10) { Game() },
        onMenuClick = {},
        onFilterClick = {},
    )
}*/