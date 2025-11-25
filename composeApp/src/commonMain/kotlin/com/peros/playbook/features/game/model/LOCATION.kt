package com.peros.playbook.features.game.model

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.indoor
import playbook.composeapp.generated.resources.outdoor

/**
 * A jatek helyszine, kategoriakban
 * INDOOR: beltéri, OUTDOOR: kulteri
 */
enum class LOCATION {
    INDOOR, OUTDOOR;

    /**
     * A LOCATION enumhoz tartozo lokalizalt szoveg visszaadasa
     * @return a lokalizalt szoveg
     */
    @Composable
    fun toDisplayString(): String {
        return when (this) {
            INDOOR -> stringResource(Res.string.indoor)
            OUTDOOR -> stringResource(Res.string.outdoor)
        }
    }
}