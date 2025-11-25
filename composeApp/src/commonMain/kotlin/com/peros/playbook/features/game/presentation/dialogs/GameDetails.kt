package com.peros.playbook.features.game.presentation.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.peros.playbook.core.platform.Platform
import com.peros.playbook.features.game.data.repository.RemoteRepository
import com.peros.playbook.features.game.model.Game
import com.peros.playbook.features.game.presentation.components.GameIcon
import com.peros.playbook.features.game.presentation.components.gameBackgroundColor
import com.peros.playbook.features.game.presentation.components.Chip
import com.peros.playbook.features.game.presentation.components.FavoriteButton
import com.peros.playbook.features.game.presentation.components.RatingStars
import com.peros.playbook.core.platform.shareGameLink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.close
import playbook.composeapp.generated.resources.none_
import playbook.composeapp.generated.resources.supplies

/**
 * A jatek reszletes adatait megjelenito dialogus
 * @param game a jatek
 * @param repository a tavoli adattar
 * @param onDismiss a dialogus bezarasa
 * @param onDelete a jatek torlese
 * @param onEdit a jatek szerkesztese
 * @param onRating a jatek ertekelese
 */
@Composable
fun GameDetailsDialog(
    game: Game,
    repository: RemoteRepository,
    onDelete: (Game) -> Unit,
    onEdit: (Game) -> Unit,
    onDismiss: () -> Unit,
    onRating: (Game) -> Unit,
) {
    val iconBackgroundColor = gameBackgroundColor(game.ageGroup[0])

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .widthIn(max = 600.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Fejlec, ikon, nev, kedvenc
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(iconBackgroundColor),
                                contentAlignment = Alignment.Center
                            ) {
                                GameIcon(
                                    game = game,
                                    color = MaterialTheme.colorScheme.surface
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = game.name,
                                style = MaterialTheme.typography.titleLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        FavoriteButton(
                            isInitiallyFavorite = game.liked,
                            onFavoriteChange = { isFav ->
                                game.liked = isFav
                            },
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.wrapContentWidth()
                        )
                    }

                    RatingStars(game.rating, game.ratingNumber)

                    Text(
                        text = game.shortDescription,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

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

                    Text(
                        text = game.longDescription,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        onRating(game)
                    }) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                            if (game.isRatinged > 0) {
                                Text(
                                    text = game.isRatinged.toString(),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            shareGameLink(
                                repository.getGameDocumentId(game)
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (Platform.isJvm) {
                        Spacer(modifier = Modifier.width(8.dp))

                        TextButton(onClick = {
                            onEdit(game)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        TextButton(
                            onClick = {
                                onDelete(game)
                                onDismiss()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(32.dp))
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(Res.string.close))
                    }
                }
            }
        }
    }
}

/**
 * Preview a GameDetailsDialog komponenshez
 */
/*@Preview
@Composable
fun GameDetailsDialogPreview() {
    GameDetailsDialog(
        game = Game(),
        onDismiss = {},
        onDelete = {},
        onEdit = {},
        onRating = {}
    )
}*/