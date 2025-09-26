package com.peros.playbook.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Tuzijatek effektus
 * @param modifier a modifer
 * @param trigger ha true, elindul az effektus
 * @param onDimiss amikor vegez az effektus
 */
@Composable
fun FireworksEffect(
    modifier: Modifier = Modifier,
    trigger: Boolean,
    onDimiss: () -> Unit
) {
    if (!trigger) return

    val particleCount = 50
    val particles = remember {
        List(particleCount) {
            mutableStateOf(
                Particle(
                    x = 0f,
                    y = 0f,
                    color = Color(
                        red = Random.nextFloat(),
                        green = Random.nextFloat(),
                        blue = Random.nextFloat(),
                        alpha = 1f
                    ),
                    angle = Random.nextFloat() * 360f,
                    speed = Random.nextFloat() * 5f + 2f,
                    size = Random.nextFloat() * 8f + 4f
                )
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { state ->
            drawCircle(
                color = state.value.color,
                radius = state.value.size,
                center = Offset(state.value.x, state.value.y)
            )
        }
    }

    LaunchedEffect(true) {
        val duration = 5000L
        val startTime = withFrameNanos { it }

        while (true) {
            val elapsed = (withFrameNanos { it } - startTime) / 1_000_000
            if (elapsed > duration) break

            particles.forEach { state ->
                val rad = Math.toRadians(state.value.angle.toDouble())
                state.value = state.value.copy(
                    x = state.value.x + cos(rad).toFloat() * state.value.speed,
                    y = state.value.y + sin(rad).toFloat() * state.value.speed
                )
            }
        }
        onDimiss()
    }
}

/**
 * Egy tuzijatek elem adatai
 * @param x az x pozicio
 * @param y az y pozicio
 * @param color a szin
 * @param angle a szog
 * @param speed a sebesseg
 * @param size a meret
 */
data class Particle(
    val x: Float,
    val y: Float,
    val color: Color,
    val angle: Float,
    val speed: Float,
    val size: Float
)
