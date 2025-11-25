package com.peros.playbook.core.platform

import androidx.compose.runtime.Composable
import com.peros.playbook.features.game.model.FilterState

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