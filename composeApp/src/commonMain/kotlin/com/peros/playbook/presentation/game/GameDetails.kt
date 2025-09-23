package com.peros.playbook.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.gearlistapp.ui.theme.AppBlue
import com.example.gearlistapp.ui.theme.AppDarkGreen
import com.example.gearlistapp.ui.theme.AppGray
import com.example.gearlistapp.ui.theme.AppYellow
import com.example.gearlistapp.ui.theme.BaseWhite
import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.Game
import com.peros.playbook.presentation.ui.FavoriteButton
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.none_
import playbook.composeapp.generated.resources.supplies

/**
 * A jatek reszletes adatait megjelenito dialogus
 * @param game a jatek
 * @param onDismiss a dialogus bezarasa
 */
@Composable
fun GameDetailsDialog(
    game: Game,
    onDismiss: () -> Unit,
) {
    val defaultTextColor = BaseWhite
    val backgroundColor = if (game.ageGroup[0] == AGEGROUP.KIDS) {
        AppYellow
    } else if (game.ageGroup[0] == AGEGROUP.TEENS) {
        AppDarkGreen
    } else if (game.ageGroup[0] == AGEGROUP.ADULTS) {
        AppGray
    } else {
        AppBlue
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Fejlec, ikon, nev, kedvenc
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(backgroundColor),
                            contentAlignment = Alignment.Center
                        ) {
                            GameIcon(game = game,
                                color = defaultTextColor)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = game.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    FavoriteButton(
                        isInitiallyFavorite = false,
                        onFavoriteChange = { isFav ->
                            game.liked = isFav
                            println("Favorite state changed: $isFav")
                        }
                    )
                }

                // Rovid leiras
                Text(
                    text = game.shortDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                // Parameterek
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (i in 0 until game.time.size) {
                        Chip("⏱ " + game.time[i].toDisplayString())
                    }
                    for (i in 0 until game.numberOfPlayers.size) {
                        Chip("👥 " + game.numberOfPlayers[i].toDisplayString())
                    }
                    for (i in 0 until game.ageGroup.size) {
                        Chip("🎯 " + game.ageGroup[i].toDisplayString())
                    }
                    for (i in 0 until game.location.size) {
                        Chip("📍 " + game.location[i].toDisplayString())
                    }

                }

                Row {
                    Text(
                        text = stringResource(Res.string.supplies) + ": ",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (game.supplies.isNotEmpty()) {
                        Chip("🧰 ${game.supplies}")
                    } else {
                        Chip(stringResource(Res.string.none_))
                    }
                }

                // Leiras
                Text(
                    text = game.longDescription,
                    style = MaterialTheme.typography.bodyLarge
                )

                // Bezaras
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

/**
 * Egy kis cimke-buborek elem, amit a jatek parametereinek megjelenitesere hasznalok
 * @param text a cimke szovege
 */
@Composable
fun Chip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Preview a GameDetailsDialog komponenshez
 */
@Preview
@Composable
fun GameDetailsDialogPreview() {
    GameDetailsDialog(
        game = Game(),
        onDismiss = {},
    )
}