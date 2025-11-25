package com.peros.playbook.features.game.data

import app.cash.sqldelight.db.SqlDriver

/**
 * Az adatbazis driver letrehozasara szolgalo osztaly
 */
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}