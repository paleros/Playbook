package com.peros.playbook.database

import android.util.Log
import com.peros.playbook.game.Game
import com.peros.playbook.presentation.home.findGameInGamesAndGetGames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
* A tavoli adatbazisbol lekeri az osszes jatekot, es ha a helyi adatbazisban nincs meg, akkor beszurja
 * @param localRepository a helyi adatbazis kezeleseert felelos osztaly
 * @param remoteRepository a tavoli adatbazis kezeleseert felelos osztaly
*/
suspend fun syncDownAllGames(
    localRepository: GameLocalRepository,
    remoteRepository: RemoteRepository,
    ) {
    withContext(Dispatchers.Default) {
        try {
            val remoteGames = remoteRepository.getAllGames()
            for (remoteGame in remoteGames) {

                val existing = localRepository.getAllGames().any { it.name == remoteGame.name }
                if (!existing) {

                    localRepository.insertGame(
                        name = remoteGame.name,
                        shortDescription = remoteGame.shortDescription,
                        longDescription = remoteGame.longDescription,
                        supplies = remoteGame.supplies,
                        numberOfPlayers = remoteGame.numberOfPlayers,
                        time = remoteGame.time,
                        ageGroup = remoteGame.ageGroup,
                        location = remoteGame.location,
                        rating = remoteGame.rating,
                        ratingNumber = remoteGame.ratingNumber,
                        liked = false
                    )
                } else {
                    val oldGame = findGameInGamesAndGetGames(remoteGame.getGame(), localRepository)
                    val newGame = remoteGame.getGame()
                    localRepository.updateGame(newGame.gameToGames(oldGame.id))
                }
            }
        } catch (e : Exception) {
            Log.e("SyncGames", "Error syncing games", e)
        }
    }
}

/**
 * Egy jatekot feltolt a tavoli adatbazisba
 * @param game a feltoltendo jatek
 * @param remoteRepository a tavoli adatbazis kezeleseert felelos osztaly
 */
suspend fun syncUpAGame(
    game: Game,
    remoteRepository: RemoteRepository,
) {
    withContext(Dispatchers.Default) {
        try {
            remoteRepository.insertGame(game)
        } catch (e: Exception) {
            Log.e("SyncGames", "Error syncing game", e)
        }
    }
}