package com.peros.playbook.database

import app.cash.sqldelight.db.SqlDriver

/**
 * Az adatbazis driver letrehozasara szolgalo osztaly
 */
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}