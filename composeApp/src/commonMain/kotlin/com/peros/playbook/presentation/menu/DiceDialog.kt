package com.peros.playbook.presentation.menu

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.cancel
import playbook.composeapp.generated.resources.dice

/**
 * Dobokocka dialogus komponens
 * Az animalashoz nem annyira ertek, ChatGPT segített
 * @param onDismiss a dialogus bezarasa
 */
@Composable
fun DiceDialog(onDismiss: () -> Unit) {
    var diceNumber by remember { mutableIntStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }

    val rotation = remember { Animatable(0f) }

    // Pottyok kirajzolasa
    @Composable
    fun DiceFace(number: Int) {
        val dotColor = MaterialTheme.colorScheme.onPrimaryContainer

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            val pattern = when (number) {
                1 -> listOf(listOf(false, false, false), listOf(false, true, false), listOf(false, false, false))
                2 -> listOf(listOf(true, false, false), listOf(false, false, false), listOf(false, false, true))
                3 -> listOf(listOf(true, false, false), listOf(false, true, false), listOf(false, false, true))
                4 -> listOf(listOf(true, false, true), listOf(false, false, false), listOf(true, false, true))
                5 -> listOf(listOf(true, false, true), listOf(false, true, false), listOf(true, false, true))
                else -> listOf(listOf(true, false, true), listOf(true, false, true), listOf(true, false, true)) // 6
            }

            pattern.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { dot ->
                        Box(
                            modifier = Modifier
                                .size(if (dot) 14.dp else 10.dp)
                                .background(
                                    color = if (dot) dotColor else Color.Transparent,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(isRolling) {
        if (isRolling) {
            repeat(2) {
                val job = launch {
                    rotation.animateTo(
                        targetValue = 360f,
                        animationSpec = tween(durationMillis = 700, easing = LinearEasing)
                    )
                }

                repeat(7) {
                    diceNumber = (1..6).random()
                    delay(100)
                }

                job.join()
                rotation.snapTo(0f)
            }
            isRolling = false
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    stringResource(Res.string.dice),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .graphicsLayer(rotationZ = rotation.value)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                        .clickable { if (!isRolling) isRolling = true },
                    contentAlignment = Alignment.Center
                ) {
                    DiceFace(diceNumber)
                }

                Button(onClick = onDismiss) {
                    Text(stringResource(Res.string.cancel))
                }
            }
        }
    }
}