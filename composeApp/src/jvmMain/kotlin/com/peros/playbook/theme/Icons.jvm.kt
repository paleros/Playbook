package com.peros.playbook.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.painterResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.crow_solid_full
import playbook.composeapp.generated.resources.frog_solid_full
import playbook.composeapp.generated.resources.horse_solid_full

/**
 * Az ikonok megfeleo megjelenitese platformonkent
 */

actual val FrogIcon: Painter
    @Composable get() = painterResource(Res.drawable.frog_solid_full)

actual val CrowIcon: Painter
    @Composable get() = painterResource(Res.drawable.crow_solid_full)

actual val HorseIcon: Painter
    @Composable get() = painterResource(Res.drawable.horse_solid_full)