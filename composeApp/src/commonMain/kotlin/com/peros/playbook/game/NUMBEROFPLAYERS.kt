package com.peros.playbook.game

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.huge_players
import playbook.composeapp.generated.resources.large_players
import playbook.composeapp.generated.resources.medium_players
import playbook.composeapp.generated.resources.small_players

/**
 * A jatekhoz szukseges jatekosok szama, kategoriakban
 * SMALL: 2-8, MEDIUM: 9-16, LARGE: 17-40, HUGE: 40+
 */
enum class NUMBEROFPLAYERS {
    SMALL, MEDIUM, LARGE, HUGE;

    /**
     * A NUMBEROFPLAYERS enumhoz tartozo lokalizalt szoveg visszaadasa
     * @return a lokalizalt szoveg
     */
    @Composable
    fun toDisplayString(): String {
        return when (this) {
            SMALL -> stringResource(Res.string.small_players)
            MEDIUM -> stringResource(Res.string.medium_players)
            LARGE -> stringResource(Res.string.large_players)
            HUGE -> stringResource(Res.string.huge_players)
        }
    }
}