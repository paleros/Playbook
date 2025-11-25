package com.peros.playbook.features.game.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.peros.playbook.database.GameDatabase

/**
 * Az adatbazis driver letrehozasara szolgalo osztaly
 */
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:game.db")
        try {
            GameDatabase.Companion.Schema.create(driver)
        } catch (_: Exception) {
            // Ha mar letezik a tabla, akkor nincs semmi
        }
        return driver
    }
}