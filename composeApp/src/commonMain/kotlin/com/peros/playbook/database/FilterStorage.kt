package com.peros.playbook.database

import androidx.compose.runtime.Composable

/**
 * A filter elmentesehez tartozo kozos osztaly
 * @param context az alkalmazas kontextusa
 */
expect class FilterStorage {

    /**
     * Mentese
     * @param state az aktualis ertek
     */
    fun save(state: FilterState)

    /**
     * Betoltese
     * @return az aktualis ertek
     */
    fun load(): FilterState
}
@Composable
expect fun createFilterStorage(): FilterStorage