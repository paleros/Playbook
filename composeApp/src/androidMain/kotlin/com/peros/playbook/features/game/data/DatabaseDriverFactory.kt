package com.peros.playbook.features.game.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.peros.playbook.database.GameDatabase

/**
 * Az adatbazis driver letrehozasara szolgalo osztaly
 * @param context Az Android kontextus, amibol az adatbazis letrehozhato
 */
actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            GameDatabase.Companion.Schema,
            context,
            "game.db"
        )
    }
}