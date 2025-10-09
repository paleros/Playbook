package com.peros.playbook.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * Csillagos ertekeles megjelenitese
 * @param maxRating a jatek ossz pontszama
 * @param ratingNumber a jatekra adott ertekelesek szama
 * @param starColor a csillagok szine
 */
@Composable
fun RatingStars(
    maxRating: Double,
    ratingNumber: Double,
    starColor: Color = MaterialTheme.colorScheme.primary,
) {
    val ratio: Double = if (ratingNumber != 0.0) (maxRating / ratingNumber) else 0.0
    val filledStars = ratio.coerceIn(0.0, 5.0)

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            val starFill = filledStars - (i - 1)

            Box(modifier = Modifier.size(28.dp)) {

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    modifier = Modifier.matchParentSize()
                )

                if (starFill > 0) {
                    val fillFraction = (starFill.coerceIn(0.0, 1.0) * 2).roundToInt() / 2f
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = starColor,
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RectangleShape)
                            .graphicsLayer {
                                clip = true
                                shape = RectangleShape
                            }
                            .drawWithContent {
                                clipRect(right = size.width * fillFraction) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                    )
                }
            }
        }
    }
}