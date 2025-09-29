package com.peros.playbook.database

import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.Game
import com.peros.playbook.game.LOCATION
import com.peros.playbook.game.NUMBEROFPLAYERS
import com.peros.playbook.game.TIME

/**
 * Az adatbazissal valo muveletek vegrehajtasara szolgalo osztaly
 * @param repository az adatbazissal valo muveletek vegrehajtasara szolgalo osztaly
 */
class GameUseCases(private val repository: GameRepository) {
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
                liked = row.liked.toInt() == 1
            )
        }
    }

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
            liked = game.liked
        )
    }
}

/**
 * A jatekosok szamanak string listajat alakitja at a megfelelo enum listava
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