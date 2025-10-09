package com.peros.playbook.database

import app.cash.sqldelight.db.SqlDriver

/**
 * A jatekok adatbazis kezeleseert felelos osztaly
 * @param driver az adatbazis driver
 */
class GameLocalRepository(driver: SqlDriver) {
    private val database = GameDatabase(driver)
    private val queries = database.gameQueries

    /**
     * Osszes jatek lekerese az adatbazisbol
     * @return a jatekok listaja
     */
    fun getAllGames() = queries.selectAll().executeAsList()

    /**
     * Uj jatek beszurasa az adatbazisba
     */
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

    /**
     * Jatek torlese az adatbazisbol
     * @param game a torlendo jatek
     */
    fun deleteGame(game: Games) {
        queries.deleteGame(game.id)
    }

    /**
     * Jatek frissitese az adatbazisban
     * @param game a frissitendo jatek
     */
    fun updateGame(game: Games) {
        queries.updateGame(
            name = game.name,
            shortDescription = game.shortDescription,
            longDescription = game.longDescription,
            supplies = game.supplies,
            numberOfPlayers = game.numberOfPlayers,
            time = game.time,
            ageGroup = game.ageGroup,
            location = game.location,
            liked = 0,
            id = game.id
        )
    }
}