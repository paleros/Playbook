package com.peros.playbook.game

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.long_time
import playbook.composeapp.generated.resources.short_time
import playbook.composeapp.generated.resources.medium_time

/**
 * A jatekhoz szukseges idotartam, kategoriakban
 * SHORT: 1-10 perc, MEDIUM: 11-30 perc, LONG: 30+ perc
 */
enum class TIME {
    SHORT, MEDIUM, LONG;

    /**
     * A TIME enumhoz tartozo lokalizalt szoveg visszaadasa
     * @return a lokalizalt szoveg
     */
    @Composable
    fun toDisplayString(): String {
        return when (this) {
            SHORT -> stringResource(Res.string.short_time)
            MEDIUM -> stringResource(Res.string.medium_time)
            LONG -> stringResource(Res.string.long_time)
        }
    }
}