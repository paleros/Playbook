package com.peros.playbook.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

/**
 * Az adatbazis driver letrehozasara szolgalo osztaly
 * @param context Az Android kontextus, amibol az adatbazis letrehozhato
 */
actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            GameDatabase.Schema,
            context,
            "game.db"
        )
    }
}