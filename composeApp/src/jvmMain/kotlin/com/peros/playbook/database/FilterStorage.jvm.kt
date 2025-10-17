package com.peros.playbook.database

import androidx.compose.runtime.Composable
import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.LOCATION
import com.peros.playbook.game.NUMBEROFPLAYERS
import com.peros.playbook.game.TIME
import java.io.File
import java.util.Properties

/**
 * A filter elmeteseert felelos osztaly
 */
actual class FilterStorage{
    private val file = File(System.getProperty("user.home"), "filter_settings.properties")

    /**
     * A mentes funkcio
     * @param state a mentett ertek
     */
    actual fun save(state: FilterState) {
        val props = Properties()
        props["players"] = state.players.joinToString(",")
        props["time"] = state.time.joinToString(",")
        props["age"] = state.age.joinToString(",")
        props["location"] = state.location.joinToString(",")
        props["noSupplies"] = state.noSupplies.toString()
        props["onlyFavorites"] = state.onlyFavorites.toString()
        props["minRating"] = state.minRating.toString()
        file.outputStream().use { props.store(it, null) }
    }

    /**
     * A betoltes funkcioja
     * @return a mentett ertek
     */
    actual fun load(): FilterState {
        if (!file.exists()) return FilterState()
        val props = Properties()
        file.inputStream().use { props.load(it) }
        return FilterState(
            players = props.getProperty("players", "").split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { enumValueOfOrNull<NUMBEROFPLAYERS>(it) }.toSet(),
            time = props.getProperty("time", "").split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { enumValueOfOrNull<TIME>(it) }.toSet(),
            age = props.getProperty("age", "").split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { enumValueOfOrNull<AGEGROUP>(it) }.toSet(),
            location = props.getProperty("location", "").split(",")
                .filter { it.isNotEmpty() }
                .mapNotNull { enumValueOfOrNull<LOCATION>(it) }.toSet(),
            noSupplies = props.getProperty("noSupplies", "false").toBoolean(),
            onlyFavorites = props.getProperty("onlyFavorites", "false").toBoolean(),
            minRating = props.getProperty("minRating", "1").toInt()
        )
    }

    private inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String): T? =
        try { enumValueOf<T>(name) } catch (_: Exception) { null }
}

/**
 * A FilterStorage peldanyositasa
 * @return a FilterStorage peldany
 */
@Composable
actual fun createFilterStorage(): FilterStorage {
    return FilterStorage()
}