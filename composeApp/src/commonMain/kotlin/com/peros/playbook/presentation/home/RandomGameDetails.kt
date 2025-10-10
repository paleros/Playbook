package com.peros.playbook.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.Game
import com.peros.playbook.presentation.game.GameIcon
import com.peros.playbook.presentation.ui.Chip
import com.peros.playbook.presentation.ui.FavoriteButton
import com.peros.playbook.presentation.ui.FireworksEffect
import com.peros.playbook.presentation.ui.RatingStars
import com.peros.playbook.theme.blue
import com.peros.playbook.theme.gray
import com.peros.playbook.theme.green
import com.peros.playbook.theme.yellow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.none_
import playbook.composeapp.generated.resources.random_game
import playbook.composeapp.generated.resources.supplies

/**
 * Egy veletlenszeruen kivalasztott jatek reszletes adatait megjelenito dialogus
 * @param selectedGames a kivalasztott jatekok listaja
 * @param onDismiss a dialogus bezarasa
 */
@Composable
fun RandomGameDetailsDialog(
    selectedGames: List<Game>,
    onDismiss: () -> Unit,
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    var showFireworks by remember { mutableStateOf(true) }


    val randomGame = selectedGames.random()

    val iconBackgroundColor = if (randomGame.ageGroup[0] == AGEGROUP.KIDS) {
        yellow
    } else if (randomGame.ageGroup[0] == AGEGROUP.TEENS) {
        green
    } else if (randomGame.ageGroup[0] == AGEGROUP.ADULTS) {
        gray
    } else {
        blue
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = primaryColor,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(2.dp, yellow, RoundedCornerShape(24.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .background(primaryColor)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "🎲 " + stringResource(Res.string.random_game) + "!",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = onPrimaryColor
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Fejlec, ikon, nev, kedvenc
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(iconBackgroundColor)
                                .border(2.dp, onPrimaryColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            GameIcon(game = randomGame, color = onPrimaryColor)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = randomGame.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = onPrimaryColor
                            )
                        )
                    }
                    FavoriteButton(
                        isInitiallyFavorite = randomGame.liked,
                        onFavoriteChange = { isFav ->
                            randomGame.liked = isFav
                        },
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                RatingStars(randomGame.rating,
                    randomGame.ratingNumber.toDouble(), 
                    onPrimaryColor)

                Text(
                    text = randomGame.shortDescription,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = onPrimaryColor.copy(alpha = 0.9f),
                        fontStyle = FontStyle.Italic
                    )
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    randomGame.time.forEach { Chip("⏱ ${it.toDisplayString()}", backgroundColor = onPrimaryColor, textColor = onPrimaryColor )}
                    randomGame.numberOfPlayers.forEach { Chip("👥 ${it.toDisplayString()}", backgroundColor = onPrimaryColor, textColor = onPrimaryColor ) }
                    randomGame.ageGroup.forEach { Chip("🎯 ${it.toDisplayString()}", backgroundColor = onPrimaryColor, textColor = onPrimaryColor ) }
                    randomGame.location.forEach { Chip("📍 ${it.toDisplayString()}", backgroundColor = onPrimaryColor, textColor = onPrimaryColor ) }
                }

                Row {
                    Text(
                        text = stringResource(Res.string.supplies) + ": ",
                        style = MaterialTheme.typography.bodyLarge.copy(color = onPrimaryColor)
                    )
                    if (randomGame.supplies.isNotEmpty()) {
                        Chip("🧰 ${randomGame.supplies}", backgroundColor = onPrimaryColor, textColor = onPrimaryColor )
                    } else {
                        Chip(stringResource(Res.string.none_), backgroundColor = onPrimaryColor, textColor = onPrimaryColor )
                    }
                }

                Text(
                    text = randomGame.longDescription,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = onPrimaryColor,
                        fontWeight = FontWeight.Medium
                    )
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .background(yellow, RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            "Close",
                            color = primaryColor,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
        if (showFireworks) {
            FireworksEffect(trigger = true, onDimiss = {showFireworks = false})
        }
    }
}

/**
 * Preview a RandomGameDetailsDialog komponenshez
 */
@Preview
@Composable
fun RandomGameDetailsDialogPreview() {
    RandomGameDetailsDialog(
        selectedGames = listOf(Game(), Game()),
        onDismiss = {},
    )
}