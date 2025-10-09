package com.peros.playbook

import com.peros.playbook.database.GameUseCases
import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.Game
import com.peros.playbook.game.LOCATION
import com.peros.playbook.game.NUMBEROFPLAYERS
import com.peros.playbook.game.TIME

/**
 * General egy listat teszt jatekokkal.
 * Minden jatek kulonbozo kombinaciokat tartalmaz a kategoriakbol.
 * @param gameUseCases az adatbazissal valo muveletek vegrehajtasara szolgalo osztaly
 */
fun generateTestGames(gameUseCases: GameUseCases){

    val numberOfPlayersList = listOf(
        NUMBEROFPLAYERS.SMALL,
        NUMBEROFPLAYERS.MEDIUM,
        NUMBEROFPLAYERS.LARGE,
        NUMBEROFPLAYERS.HUGE
    )
    val timeList = listOf(
        TIME.SHORT,
        TIME.MEDIUM,
        TIME.LONG
    )
    val locationList = listOf(
        LOCATION.INDOOR,
        LOCATION.OUTDOOR,
    )
    val ageGroupList = listOf(
        AGEGROUP.KIDS,
        AGEGROUP.TEENS,
        AGEGROUP.PRETEENS,
        AGEGROUP.ADULTS,
    )


    for (i in 1..10) {
        val game = Game(
            name = "Game $i",
            shortDescription = "This is the description for Game $i.",
            longDescription = "This is the description for Game $i. It is a fun and exciting game that you will enjoy playing.",
            supplies = "Some supplies for Game $i",
            numberOfPlayers = generateRandomList(numberOfPlayersList),
            location = generateRandomList(locationList),
            time = generateRandomList(timeList),
            ageGroup = generateRandomList(ageGroupList),
            rating = (10..50).random().toDouble(),
            ratingNumber = 10,
            isRatinged = false,
            liked = i % 2 == 0
        )
        gameUseCases.insertGame(game)
    }
}

/**
 * Visszaad egy random listat a megadott elemekbol.
 * A lista hossza 1 es az elemek szama kozott valtozik.
 */
fun <T> generateRandomList(items: List<T>): List<T> {
    return items.shuffled().take((1..items.size).random())
}