package com.peros.playbook.database

import com.peros.playbook.game.Game
import com.peros.playbook.game.GameForFirebase
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Az adatbazissal valo muveletek vegrehajtasara szolgalo osztaly, Google Firestore REST API-hoz optimalizalva
 */
class GameAPIRemoteRepository : RemoteRepository {
    val projectId = "playbook-ffca5"
    val apiKey = "AIzaSyC4vw9Js77z03FkeThv69kqsyImqX4lb9Q"
    private val client = HttpClient{
        install(ContentNegotiation) {
            json()
        }
    }
    private val json = Json { ignoreUnknownKeys = true }
    val url = "https://firestore.googleapis.com/v1/projects/$projectId/databases/(default)/documents/games?key=$apiKey"

    /**
     * Osszes jatek lekerese a Firestore adatbazisbol
     * @return a jatekok listaja
     */
    override suspend fun getAllGames(): List<GameForFirebase> {
        val response: HttpResponse = client.get(url)

        if (!response.status.isSuccess()) {
            throw IllegalStateException("Firestore error: ${response.status}")
        }

        val body = response.bodyAsText()
        println(body)

        val parsed = json.parseToJsonElement(body)
        val documents = parsed.jsonObject["documents"]?.jsonArray ?: return emptyList()

        return documents.mapNotNull { doc ->
            val fields = doc.jsonObject["fields"]?.jsonObject ?: return@mapNotNull null
            GameForFirebase(
                name = fields["name"]?.jsonObject?.get("stringValue")?.toString()?.trim('"') ?: "",
                shortDescription = fields["shortDescription"]?.jsonObject?.get("stringValue")?.toString()?.trim('"') ?: "",
                longDescription = fields["longDescription"]?.jsonObject?.get("stringValue")?.toString()?.trim('"') ?: "",
                supplies = fields["supplies"]?.jsonObject?.get("stringValue")?.toString()?.trim('"') ?: "",
                numberOfPlayers = fields["numberOfPlayers"]?.jsonObject?.get("stringValue")?.toString()?.trim('"') ?: "",
                time = fields["time"]?.jsonObject?.get("stringValue")?.toString()?.trim('"') ?: "",
                ageGroup = fields["ageGroup"]?.jsonObject?.get("stringValue")?.toString()?.trim('"') ?: "",
                location = fields["location"]?.jsonObject?.get("stringValue")?.toString()?.trim('"') ?: "",
                rating = fields["rating"]?.jsonObject?.get("stringValue")?.toString()?.trim('"') ?: "",
                ratingNumber = fields["ratingNumber"]?.jsonObject?.get("stringValue")?.toString()?.trim('"') ?: ""
            )
        }
    }

    /**
     * Uj jatek beszurasa a Firestore adatbazisba
     * @param game a beszurendo jatek
     */
    override suspend fun insertGame(game: Game) {
        val firebaseGame = game.gameToFirebase()

        val bodyJson =   """
                {
                  "fields": {
                    "name": { "stringValue": "${firebaseGame.name}" },
                    "shortDescription": { "stringValue": "${firebaseGame.shortDescription}" },
                    "longDescription": { "stringValue": "${firebaseGame.longDescription}" },
                    "supplies": { "stringValue": "${firebaseGame.supplies}" },
                    "numberOfPlayers": { "stringValue": "${firebaseGame.numberOfPlayers}" },
                    "time": { "stringValue": "${firebaseGame.time}" },
                    "ageGroup": { "stringValue": "${firebaseGame.ageGroup}" },
                    "location": { "stringValue": "${firebaseGame.location}" },
                    "ratingNumber": { "stringValue": "${firebaseGame.ratingNumber}" },
                    "rating": { "stringValue": "${firebaseGame.rating}" }
                  }
                }
                """.trimIndent()

        val response: HttpResponse = client.post(url){
            contentType(ContentType.Application.Json)
            setBody(bodyJson)
        }

        if (!response.status.isSuccess()) {
            throw IllegalStateException("Firestore error: ${response.status}")
        }

        val body = response.bodyAsText()
        println(body)
    }

    /**
     * Jatek torlese a Firestore adatbazisbol
     * @param game a torlendo jatek
     */
    override suspend fun deleteGame(game: Game) {
        val listResponse: HttpResponse = client.get(url)

        if (!listResponse.status.isSuccess()) {
            throw IllegalStateException("Firestore error: ${listResponse.status}")
        }

        val body = listResponse.bodyAsText()
        val parsed = json.parseToJsonElement(body)
        val documents = parsed.jsonObject["documents"]?.jsonArray ?: return

        val documentToDelete = documents.firstOrNull { doc ->
            val fields = doc.jsonObject["fields"]?.jsonObject
            val nameValue = fields?.get("name")?.jsonObject?.get("stringValue")?.toString()?.trim('"')
            nameValue == game.name
        } ?: return

        val docName = documentToDelete.jsonObject["name"]?.jsonPrimitive?.content ?: return

        val deleteUrl = "https://firestore.googleapis.com/v1/$docName?key=$apiKey"

        val deleteResponse: HttpResponse = client.delete(deleteUrl)

        if (!deleteResponse.status.isSuccess()) {
            throw IllegalStateException("Firestore delete error: ${deleteResponse.status}")
        }

        println("Game '${game.name}' successfully deleted.")
    }

    /**
     * Jatek frissitese a Firestore adatbazisban
     * @param game a frissitendo jatek
     * @param oldName a jatek regi neve
     */
    override suspend fun updateGame(game: Game, oldName: String) {
        val listResponse: HttpResponse = client.get(url)

        if (!listResponse.status.isSuccess()) {
            throw IllegalStateException("Firestore error: ${listResponse.status}")
        }

        val body = listResponse.bodyAsText()
        val parsed = json.parseToJsonElement(body)
        val documents = parsed.jsonObject["documents"]?.jsonArray ?: return

        val documentToUpdate = documents.firstOrNull { doc ->
            val fields = doc.jsonObject["fields"]?.jsonObject
            val nameValue = fields?.get("name")?.jsonObject?.get("stringValue")?.toString()?.trim('"')
            nameValue == oldName
        } ?: run {
            println("Game '${oldName}' not found in Firestore.")
            return
        }

        val docName = documentToUpdate.jsonObject["name"]?.jsonPrimitive?.content ?: return

        val firebaseGame = game.gameToFirebase()
        val updateUrl = "https://firestore.googleapis.com/v1/$docName?key=$apiKey"

        val updateResponse: HttpResponse = client.patch(updateUrl) {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                  "fields": {
                    "name": { "stringValue": "${firebaseGame.name}" },
                    "shortDescription": { "stringValue": "${firebaseGame.shortDescription}" },
                    "longDescription": { "stringValue": "${firebaseGame.longDescription}" },
                    "supplies": { "stringValue": "${firebaseGame.supplies}" },
                    "numberOfPlayers": { "stringValue": "${firebaseGame.numberOfPlayers}" },
                    "time": { "stringValue": "${firebaseGame.time}" },
                    "ageGroup": { "stringValue": "${firebaseGame.ageGroup}" },
                    "location": { "stringValue": "${firebaseGame.location}" },
                    "rating": { "stringValue": "${firebaseGame.rating}" },
                    "ratingNumber": { "stringValue": "${firebaseGame.ratingNumber}" }
                  }
                }
                """.trimIndent()
            )
        }

        if (!updateResponse.status.isSuccess()) {
            throw IllegalStateException("Firestore update error: ${updateResponse.status}")
        }

        println("Game '${game.name}' successfully updated.")
    }

    /**
     * Jatek dokumentumazonositojanak lekerese a Firestore adatbazisbol
     * @param game a jatek, aminek a dokumentumazonositojat le
     * @return a jatek dokumentumazonositoja, vagy null, ha nem letezik
     */
    override suspend fun getGameDocumentId(game: Game): String? {
        val listResponse: HttpResponse = client.get(url)

        if (!listResponse.status.isSuccess()) {
            throw IllegalStateException("Firestore error: ${listResponse.status}")
        }

        val body = listResponse.bodyAsText()
        val parsed = json.parseToJsonElement(body)
        val documents = parsed.jsonObject["documents"]?.jsonArray ?: return null

        val document = documents.firstOrNull { doc ->
            val fields = doc.jsonObject["fields"]?.jsonObject
            val nameValue = fields?.get("name")?.jsonObject?.get("stringValue")?.toString()?.trim('"')
            nameValue == game.name
        } ?: return null

        return document.jsonObject["name"]?.jsonPrimitive?.content
    }
}