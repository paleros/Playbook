package com.peros.playbook.database

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.LOCATION
import com.peros.playbook.game.NUMBEROFPLAYERS
import com.peros.playbook.game.TIME

/**
 * A filter elmeteseert felelos osztaly Android platformon
 * @param context az alkalmazas kontextusa
 */
actual class FilterStorage(private val context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("filter_prefs", Context.MODE_PRIVATE)

    /**
     * Ment egy szurot
     * @param state a szuro
     */
    actual fun save(state: FilterState) {
        prefs.edit().apply {
            putString("players", state.players.joinToString(","))
            putString("time", state.time.joinToString(","))
            putString("age", state.age.joinToString(","))
            putString("location", state.location.joinToString(","))
            putBoolean("noSupplies", state.noSupplies)
            putBoolean("onlyFavorites", state.onlyFavorites)
            putInt("minRating", state.minRating)
            apply()
        }
    }

    /**
     * Betolti egy szurot
     * @return a betolt szuro
     */
    actual fun load(): FilterState {
        return FilterState(
            players = prefs.getString("players", "")!!.split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { enumValueOfOrNull<NUMBEROFPLAYERS>(it) }.toSet(),
            time = prefs.getString("time", "")!!.split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { enumValueOfOrNull<TIME>(it) }.toSet(),
            age = prefs.getString("age", "")!!.split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { enumValueOfOrNull<AGEGROUP>(it) }.toSet(),
            location = prefs.getString("location", "")!!.split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { enumValueOfOrNull<LOCATION>(it) }.toSet(),
            noSupplies = prefs.getBoolean("noSupplies", false),
            onlyFavorites = prefs.getBoolean("onlyFavorites", false),
            minRating = prefs.getInt("minRating", 1)
        )
    }

    private inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String): T? =
        try { enumValueOf<T>(name) } catch (_: Exception) { null }
}

/**
 * Létrehoz egy FilterStorage példányt
 * @return a FilterStorage példány
 */
@Composable
actual fun createFilterStorage(): FilterStorage {
    val context = LocalContext.current
    return FilterStorage(context)
}