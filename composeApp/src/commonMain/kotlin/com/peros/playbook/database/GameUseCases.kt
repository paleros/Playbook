package com.peros.playbook.database

import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.Game
import com.peros.playbook.game.LOCATION
import com.peros.playbook.game.NUMBEROFPLAYERS
import com.peros.playbook.game.TIME

/**
 * Az adatbazissal valo muveletek vegrehajtasara szolgalo osztaly
 * @param repository az adatbazissal valo muveletek vegrehajtasara szolgalo osztaly
 * @param remoteRepository a tavoli adatbazissal valo muveletek vegrehajtasara szolgalo osztaly
 */
class GameUseCases(
    val repository: GameLocalRepository,
    val remoteRepository: RemoteRepository
) {
    /**
     * Osszes jatek lekerese a helyi adatbazisbol
     * @return a jatekok listaja
     */
    fun getAllGames(): List<Game> {
        return repository.getAllGames().map { row ->
            Game(
                name = row.name,
                shortDescription = row.shortDescription,
                longDescription = row.longDescription,
                supplies = row.supplies,
                numberOfPlayers = convertNumberOfPlayers(row.numberOfPlayers.split(",")),
                time = convertTime(row.time.split(",")),
                ageGroup = convertAgeGroup(row.ageGroup.split(",")),
                location = convertLocation(row.location.split(",")),
                rating = row.rating.toInt(),
                ratingNumber = row.ratingNumber.toInt(),
                isRatinged = row.isRatinged.toInt() == 1,
                liked = row.liked.toInt() == 1
            )
        }
    }

    /**
     * Uj jatek beszurasa a helyi adatbazisba
     * @param game a beszurando jatek
     */
    fun insertGame(game: Game) {
        repository.insertGame(
            name = game.name,
            shortDescription = game.shortDescription,
            longDescription = game.longDescription,
            supplies = game.supplies,
            numberOfPlayers = game.numberOfPlayers.joinToString(","),
            time = game.time.joinToString(","),
            ageGroup = game.ageGroup.joinToString(","),
            location = game.location.joinToString(","),
            rating = game.rating,
            ratingNumber = game.ratingNumber,
            isRatinged = game.isRatinged,
            liked = game.liked
        )
    }

    /**
     * Jatek torlese a helyi adatbazisbol
     * @param game a torlendo jatek
     */
    fun deleteGame(game: Games) {
        repository.deleteGame(game)
    }

    /**
     * Jatek torlese a tavoli adatbazisban
     * @param game a frissitendo jatek
     */
    suspend fun deleteRemoteGame(game: Game) {
        remoteRepository.deleteGame(game)

    }

    /**
     * Jatek frissitese a tavoli adatbazisban
     * @param game a frissitendo jatek
     * @param oldName a jatek regi neve (a Firestore-ban ez azonosito)
     */
    suspend fun updateRemoteGame(game: Game, oldName: String) {
        remoteRepository.updateGame(game, oldName)

    }

    /**
     * Jatek frissitese a helyi adatbazisban
     * @param game a frissitendo jatek
     */
    fun updateGame(game: Games) {
        repository.updateGame(game)

    }

    /**
     * A tavoli adatbazisbol lekeri az osszes jatekot, es ha a helyi adatbazisban nincs meg, akkor beszurja
     */
    suspend fun syncDown() {
        syncDownAllGames(repository, remoteRepository)
    }

    /**
     * A helyi adatbazisban levo jatekot feltolti a tavoli adatbazisba
     * @param game a feltoltendo jatek
     */
    suspend fun syncUp(game: Game) {
        syncUpAGame(game, remoteRepository)
    }
}

/**
 * A jatekosok szamanak string listajat alakitja at a megfelelo enum listava
 * @param list a jatekosok szamanak string listaja
 * @return a jatekosok szamanak enum listaja
 */
fun convertNumberOfPlayers(list: List<String>): List<NUMBEROFPLAYERS> {
    val enumList = mutableListOf<NUMBEROFPLAYERS>()
    for (item in list) {
        when (item) {
            "SMALL" -> enumList.add(NUMBEROFPLAYERS.SMALL)
            "MEDIUM" -> enumList.add(NUMBEROFPLAYERS.MEDIUM)
            "LARGE" -> enumList.add(NUMBEROFPLAYERS.LARGE)
            "HUGE" -> enumList.add(NUMBEROFPLAYERS.HUGE)
        }
    }
        return enumList
}

/**
 * A jatekido string listajat alakitja at a megfelelo enum listava
 * @param list a jatekido string listaja
 * @return a jatekido enum listaja
 */
fun convertTime(list: List<String>): List<TIME> {
    val enumList = mutableListOf<TIME>()
    for (item in list) {
        when (item) {
            "SHORT" -> enumList.add(TIME.SHORT)
            "MEDIUM" -> enumList.add(TIME.MEDIUM)
            "LONG" -> enumList.add(TIME.LONG)
        }
    }
    return enumList
}

/**
 * A korosztalyok string listajat alakitja at a megfelelo enum listava
 * @param list a korosztalyok string listaja
 * @return a korosztalyok enum listaja
 */
fun convertAgeGroup(list: List<String>): List<AGEGROUP> {
    val enumList = mutableListOf<AGEGROUP>()
    for (item in list) {
        when (item) {
            "KIDS" -> enumList.add(AGEGROUP.KIDS)
            "PRETEENS" -> enumList.add(AGEGROUP.PRETEENS)
            "TEENS" -> enumList.add(AGEGROUP.TEENS)
            "ADULTS" -> enumList.add(AGEGROUP.ADULTS)
        }
    }
    return enumList
}

/**
 * A jatekido string listajat alakitja at a megfelelo enum listava
 * @param list a jatekido string listaja
 * @return a jatekido enum listaja
 */
fun convertLocation(list: List<String>): List<LOCATION> {
    val enumList = mutableListOf<LOCATION>()
    for (item in list) {
        when (item) {
            "INDOOR" -> enumList.add(LOCATION.INDOOR)
            "OUTDOOR" -> enumList.add(LOCATION.OUTDOOR)
        }
    }
    return enumList
}