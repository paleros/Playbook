package com.peros.playbook.database

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.peros.playbook.game.Game
import com.peros.playbook.game.GameForFirebase
import kotlinx.coroutines.tasks.await

/**
 * Az adatbazissal valo muveletek vegrehajtasara szolgalo osztaly, Firebase-hez optimalizalva
 */
class GameRemoteRepository : RemoteRepository {
    val firestore = Firebase.firestore

    /**
     * Osszes jatek lekerese a Firestore adatbazisbol
     */
    override suspend fun getAllGames(): List<GameForFirebase> {
        val snapshot = firestore.collection("games").get().await()
        return snapshot.documents.map { doc ->
            GameForFirebase(
                name = doc["name"] as String,
                shortDescription = doc["shortDescription"] as String,
                longDescription = doc["longDescription"] as String,
                supplies = doc["supplies"] as String,
                numberOfPlayers = doc["numberOfPlayers"] as String,
                time = doc["time"] as String,
                ageGroup = doc["ageGroup"] as String,
                location = doc["location"] as String,
            )
        }
    }

    /**
     * Uj jatek beszurasa a Firestore adatbazisba
     */
    override suspend fun insertGame(game: Game) {
        val firebaseGame = game.gameToFirebase()
        firestore.collection("games").add(mapOf(
            "name" to firebaseGame.name,
            "shortDescription" to firebaseGame.shortDescription,
            "longDescription" to firebaseGame.longDescription,
            "supplies" to firebaseGame.supplies,
            "numberOfPlayers" to firebaseGame.numberOfPlayers,
            "time" to firebaseGame.time,
            "ageGroup" to firebaseGame.ageGroup,
            "location" to firebaseGame.location,
        ))
    }
}