package com.peros.playbook.database

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
* A tavoli adatbazisbol lekeri az osszes jatekot, es ha a helyi adatbazisban nincs meg, akkor beszurja
 * @param localRepository a helyi adatbazis kezeleseert felelos osztaly
 * @param remoteRepository a tavoli adatbazis kezeleseert felelos osztaly
*/
suspend fun syncDownGames(
    localRepository: GameLocalRepository,
    remoteRepository: GameRemoteRepository,
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
                        liked = false
                    )
                }
            }
        } catch (e : Exception) {
            Log.e("SyncGames", "Error syncing games", e)
        }
    }
}