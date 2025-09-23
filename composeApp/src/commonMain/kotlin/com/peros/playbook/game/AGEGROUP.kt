package com.peros.playbook.game

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.adults
import playbook.composeapp.generated.resources.kids
import playbook.composeapp.generated.resources.preteens
import playbook.composeapp.generated.resources.teens

/**
 * A jatekhoz alkalmas korosztaly, kategoriakban
 * KIDS: 6-10, PRETEENS: 11-14, TEENS: 15-17, ADULTS: 18+
 */
enum class AGEGROUP {
    KIDS, PRETEENS, TEENS, ADULTS;

    /**
     * Az AGEGROUP enumhoz tartozo lokalizalt szoveg visszaadasa
     * @return a lokalizalt szoveg
     */
    @Composable
    fun toDisplayString(): String {
        return when (this) {
            KIDS -> stringResource(Res.string.kids)
            PRETEENS -> stringResource(Res.string.preteens)
            TEENS -> stringResource(Res.string.teens)
            ADULTS -> stringResource(Res.string.adults)
        }
    }
}