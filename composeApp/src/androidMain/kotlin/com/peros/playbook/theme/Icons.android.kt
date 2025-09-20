package com.peros.playbook.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.peros.playbook.R

/**
 * Az ikonok megfeleo megjelenitese platformonkent
 */

actual val FrogIcon: Painter
    @Composable get() = painterResource(R.drawable.frog_solid_full)

actual val CrowIcon: Painter
    @Composable get() = painterResource(R.drawable.crow_solid_full)

actual val HorseIcon: Painter
    @Composable get() = painterResource(R.drawable.horse_solid_full)