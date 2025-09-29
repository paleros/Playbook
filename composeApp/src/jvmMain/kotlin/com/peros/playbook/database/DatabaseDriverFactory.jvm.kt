package com.peros.playbook.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

/**
 * Az adatbazis driver letrehozasara szolgalo osztaly
 */
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:game.db")
        try {
            GameDatabase.Schema.create(driver)
        } catch (_: Exception) {
            // Ha mar letezik a tabla, akkor nincs semmi
        }
        return driver
    }
}