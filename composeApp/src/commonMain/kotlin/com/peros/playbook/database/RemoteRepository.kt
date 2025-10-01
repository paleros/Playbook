package com.peros.playbook.database

import com.peros.playbook.game.Game
import com.peros.playbook.game.GameForFirebase

/**
 * A tavoli adatbazissal valo muveletek vegrehajtasara szolgalo interfesz
 */
interface RemoteRepository {
    /**
     * Osszes jatek lekerese a tavoli adatbazisbol
     * @return a jatekok listaja
     */
    suspend fun getAllGames(): List<GameForFirebase>
    /**
     * Uj jatek beszurasa a tavoli adatbazisba
     * @param game a beszurando jatek
     */
    suspend fun insertGame(game: Game)
}