package com.peros.playbook.database

import app.cash.sqldelight.db.SqlDriver

/**
 * A jatekok adatbazis kezeleseert felelos osztaly
 * @param driver az adatbazis driver
 */
class GameRepository(driver: SqlDriver) {
    private val database = GameDatabase(driver)
    private val queries = database.gameQueries

    fun getAllGames() = queries.selectAll().executeAsList()

    fun insertGame(
        name: String,
        shortDescription: String,
        longDescription: String,
        supplies: String,
        numberOfPlayers: String,
        time: String,
        ageGroup: String,
        location: String,
        liked: Boolean
    ) {
        queries.insertGame(
            name,
            shortDescription,
            longDescription,
            supplies,
            numberOfPlayers,
            time,
            ageGroup,
            location,
            if (liked) 1 else 0
        )
    }
}