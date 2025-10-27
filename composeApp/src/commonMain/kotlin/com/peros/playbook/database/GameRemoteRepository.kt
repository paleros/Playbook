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
                rating = doc["rating"] as String,
                ratingNumber = doc["ratingNumber"] as String,
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
            "rating" to firebaseGame.rating,
            "ratingNumber" to firebaseGame.ratingNumber,
        ))
    }

    /**
     * Jatek torlese a Firestore adatbazisbol
     * @param game a torlendo jatek
     */
    override suspend fun deleteGame(game: Game) {
        //Az android alkalmazasba szandekosan nincsen eze a funkcio
    }

    /**
     * Jatek frissitese a Firestore adatbazisban
     * @param game a frissitendo jatek
     * @param oldName a jatek regi neve (a Firestore-ban ez azonosito)
     */
    override suspend fun updateGame(game: Game, oldName: String) {
        val firebaseGame = game.gameToFirebase()
        val querySnapshot = firestore.collection("games")
            .whereEqualTo("name", oldName)
            .get()
            .await()

        if (querySnapshot.documents.isNotEmpty()) {
            val documentId = querySnapshot.documents[0].id
            firestore.collection("games").document(documentId).set(mapOf(
                "name" to firebaseGame.name,
                "shortDescription" to firebaseGame.shortDescription,
                "longDescription" to firebaseGame.longDescription,
                "supplies" to firebaseGame.supplies,
                "numberOfPlayers" to firebaseGame.numberOfPlayers,
                "time" to firebaseGame.time,
                "ageGroup" to firebaseGame.ageGroup,
                "location" to firebaseGame.location,
                "rating" to firebaseGame.rating,
                "ratingNumber" to firebaseGame.ratingNumber,
            )).await()
        } else {
            throw IllegalStateException("No document found with name: $oldName")
        }
    }

    /**
     * Jatek dokumentumazonositojanak lekerese a Firestore adatbazisbol
     * @param game a jatek
     * @return a jatek dokumentumazonositoja, vagy null ha nem letezik
     */
    override suspend fun getGameDocumentId(game: Game): String? {
        val querySnapshot = firestore.collection("games")
            .whereEqualTo("name", game.name)
            .get()
            .await()

        return querySnapshot.documents.firstOrNull()?.id
    }
}