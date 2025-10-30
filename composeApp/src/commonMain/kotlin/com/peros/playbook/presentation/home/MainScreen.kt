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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
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
import com.peros.playbook.database.GameLocalRepository
import com.peros.playbook.database.GameUseCases
import com.peros.playbook.database.Games
import com.peros.playbook.database.NoInternetAlertDialog
import com.peros.playbook.database.createFilterStorage
import com.peros.playbook.database.isNetworkAvailable
import com.peros.playbook.game.Game
import com.peros.playbook.presentation.game.GameCard
import com.peros.playbook.presentation.game.GameDetailsDialog
import com.peros.playbook.presentation.menu.AboutDialog
import com.peros.playbook.presentation.menu.AddGameDialog
import com.peros.playbook.presentation.menu.ConfirmDeleteDialog
import com.peros.playbook.presentation.menu.DiceDialog
import com.peros.playbook.presentation.menu.FilterDialog
import com.peros.playbook.presentation.ui.RatingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.about
import playbook.composeapp.generated.resources.dice
import playbook.composeapp.generated.resources.internet_connection_required_to_create_game
import playbook.composeapp.generated.resources.internet_connection_required_to_rating_game
import playbook.composeapp.generated.resources.internet_connection_required_to_update_games
import playbook.composeapp.generated.resources.internet_connection_required_to_edit_game
import playbook.composeapp.generated.resources.internet_connection_required_to_delete_game
import playbook.composeapp.generated.resources.menu
import playbook.composeapp.generated.resources.no_games_found
import playbook.composeapp.generated.resources.no_internet_connection
import playbook.composeapp.generated.resources.update_games

//TODO frissitesek
//TODO design
//TODO extra funkciok: kep
//TODO sajat firebase az apphoz
//TODO play?
/**
 * A fo kepernyo, ami a jatekok listajat jeleniti meg
 * @param gameList a jatekok listaja
 * @param gameUseCases a jatekokkal kapcsolatos use case-ek
 */
@Suppress("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    gameList: MutableState<List<Game>>,
    gameUseCases: GameUseCases,
    ) {
    var showFilterDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }
    var showRandomGameDialog by remember { mutableStateOf(false) }
    var showNewGameDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showRatingDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var showNoInternetDialog by remember { mutableStateOf(false) }
    var selectedGame by remember { mutableStateOf<Game?>(null) }
    var selectedGameForDelete by remember { mutableStateOf<Game?>(null) }
    var selectedGameForEdit by remember { mutableStateOf<Game?>(null) }
    var selectedGameForRating by remember { mutableStateOf<Game?>(null) }
    var sortState by remember { mutableStateOf(SORTSTATE.NAMEASC) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val filterStorage = createFilterStorage()
    var noInternetMessage by remember { mutableStateOf(Res.string.no_internet_connection)}

    var games by remember { mutableStateOf(gameList.value) }

    var filterState by remember { mutableStateOf(filterStorage.load()) }


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
                            (searchQuery.isBlank() || game.name.contains(searchQuery, ignoreCase = true)) &&
                            (game.ratingNumber > 0 && game.rating > 0 && (game.rating / game.ratingNumber) >= filterState.minRating)

                }
                .sortedWith { game1, game2 ->
                    when (sortState) {
                        SORTSTATE.NAMEASC -> game1.name.compareTo(game2.name)
                        SORTSTATE.NAMEDESC -> game2.name.compareTo(game1.name)
                    }
                }
        }
    }

    var showFilterIndicator by remember {
        mutableStateOf(
            filterState.players.isNotEmpty() ||
                    filterState.time.isNotEmpty() ||
                    filterState.age.isNotEmpty() ||
                    filterState.location.isNotEmpty() ||
                    filterState.noSupplies ||
                    filterState.onlyFavorites ||
                    filterState.minRating != 1
        )
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
                        imageVector = Icons.Default.Lightbulb,
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
                        scope.launch { drawerState.close()}
                        if (!isNetworkAvailable()) {
                            noInternetMessage = Res.string.internet_connection_required_to_update_games
                            showNoInternetDialog = true
                            return@NavigationDrawerItem
                        }
                        isLoading = true
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

                PlatformSpecificDrawerItems(
                    onClick = { showNewGameDialog = true },
                    onNoInternet = {
                        noInternetMessage = Res.string.internet_connection_required_to_create_game
                        showNoInternetDialog = true
                                   },
                    isNetworkAvailable = isNetworkAvailable(),)

                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.dice)) },
                    selected = false,
                    onClick = { showDiceDialog = true }
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
                    },
                    showIndicator = showFilterIndicator
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    if (filteredAndSortedGames.isEmpty()) {
                        Text(
                            text = stringResource(Res.string.no_games_found),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(filteredAndSortedGames) { game ->
                                GameCard(
                                    game = game,
                                    onClick = { selectedGame = game }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (selectedGame != null) {
        GameDetailsDialog(
            game = selectedGame!!,
            repository = gameUseCases.remoteRepository,
            onDismiss = { selectedGame = null
                            filterState = filterState.copy()},
            onEdit = {
                if (!isNetworkAvailable()) {
                    noInternetMessage = Res.string.internet_connection_required_to_edit_game
                    showNoInternetDialog = true
                } else {
                    selectedGameForEdit = it
                    showEditDialog = true
                    selectedGame = null
                    filterState = filterState.copy()
                }
            },
            onDelete = {
                if (!isNetworkAvailable()) {
                    noInternetMessage = Res.string.internet_connection_required_to_delete_game
                    showNoInternetDialog = true
                } else {
                selectedGameForDelete = it
                showDeleteConfirm = true
                selectedGame = null
                }
            },
            onRating = {
                if (!isNetworkAvailable()) {
                    noInternetMessage = Res.string.internet_connection_required_to_rating_game
                    showNoInternetDialog = true
                } else {
                    selectedGameForRating = it
                    showRatingDialog = true
                    selectedGame = null
                }
            }
        )
    }

    if (showDeleteConfirm) {
        ConfirmDeleteDialog(
            itemName = selectedGameForDelete!!.name,
            onDismiss = { showDeleteConfirm = false },
            onConfirm = {
                isLoading = true
                scope.launch { drawerState.close()}
                CoroutineScope(Dispatchers.IO).launch {

                    gameUseCases.deleteGame(
                        game = findGameInGamesAndGetGames(
                            selectedGameForDelete!!,
                            gameUseCases.repository
                        )
                    )

                    gameUseCases.deleteRemoteGame(
                        game = selectedGameForDelete!!
                    )
                    val updatedGames = gameUseCases.getAllGames()
                    withContext(Dispatchers.Main) {
                        games = updatedGames
                        isLoading = false
                    }
                }
            }
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
                filterStorage.save(newFilterState)
                showFilterIndicator =
                    filterState.players.isNotEmpty() ||
                            filterState.time.isNotEmpty() ||
                            filterState.age.isNotEmpty() ||
                            filterState.location.isNotEmpty() ||
                            filterState.noSupplies ||
                            filterState.onlyFavorites ||
                            filterState.minRating != 1
                showFilterDialog = false} )
    }
    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }

    if (showDiceDialog) {
        DiceDialog(onDismiss = { showDiceDialog = false })
    }

    if (showNewGameDialog) {
        AddGameDialog(onDismiss = { showNewGameDialog = false }, onSave = {
            isLoading = true
            scope.launch { drawerState.close()}
            CoroutineScope(Dispatchers.IO).launch {
                gameUseCases.insertGame(it)
                gameUseCases.syncUp(it)

                val updatedGames = gameUseCases.getAllGames()
                withContext(Dispatchers.Main) {
                    games = updatedGames
                    isLoading = false
                }
            }
        },
            existingGames = games)
    }

    if (showEditDialog){
        AddGameDialog(onDismiss = { showEditDialog = false }, onSave = {
            isLoading = true
            scope.launch { drawerState.close()}
            CoroutineScope(Dispatchers.IO).launch {
                val originalGame = findGameInGamesAndGetGames(selectedGameForEdit!!,
                    gameUseCases.repository)

                gameUseCases.updateGame(game = it.gameToGames(originalGame.id) )
                gameUseCases.updateRemoteGame(it, originalGame.name)

                val updatedGames = gameUseCases.getAllGames()
                withContext(Dispatchers.Main) {
                    games = updatedGames
                    isLoading = false
                }
            }
        },
            existingGames = games,
            isEdit = true,
            defaultGame = selectedGameForEdit!!,)
    }

    RatingDialog(onSave = { newRating ->
        isLoading = true
        scope.launch { drawerState.close()}
        CoroutineScope(Dispatchers.IO).launch {
            val originalGame = findGameInGamesAndGetGames(selectedGameForRating!!,
                gameUseCases.repository)
            val newGame = Game(
                name = selectedGameForRating!!.name,
                shortDescription = selectedGameForRating!!.shortDescription,
                longDescription = selectedGameForRating!!.longDescription,
                supplies = selectedGameForRating!!.supplies,
                numberOfPlayers = selectedGameForRating!!.numberOfPlayers,
                time = selectedGameForRating!!.time,
                ageGroup = selectedGameForRating!!.ageGroup,
                location = selectedGameForRating!!.location,
                rating = selectedGameForRating!!.rating + newRating,
                ratingNumber = selectedGameForRating!!.ratingNumber + 1,
                isRatinged = true,
                liked = selectedGameForRating!!.liked
            )

            gameUseCases.updateGame(game = newGame.gameToGames(originalGame.id) )
            gameUseCases.updateRemoteGame(newGame, originalGame.name)

            val updatedGames = gameUseCases.getAllGames()

                withContext(Dispatchers.Main) {

                    games = updatedGames

                    isLoading = false
                }
            }
                              },
            onDismiss = { showRatingDialog = false },
            showDialog = showRatingDialog
        )

    NoInternetAlertDialog(
        text = stringResource(noInternetMessage),
        onDismiss = { showNoInternetDialog = false },
        showDialog = showNoInternetDialog
    )
}

/**
 * A jatek nevet hasznalva megkeresi a jatekot a Games listaban, es visszaadja a megfelelo Games objektumot
 * @param game a keresendo jatek
 * @param repository a jatekok helyi repository-ja
 * @return a megfelelo Games objektum
 */
fun findGameInGamesAndGetGames(game: Game, repository: GameLocalRepository): Games {
    val allGames = repository.getAllGames()
    return allGames.first { it.name == game.name }
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