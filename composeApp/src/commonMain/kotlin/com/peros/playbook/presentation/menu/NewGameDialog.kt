package com.peros.playbook.presentation.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.Game
import com.peros.playbook.game.LOCATION
import com.peros.playbook.game.NUMBEROFPLAYERS
import com.peros.playbook.game.TIME
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.add_new_game
import playbook.composeapp.generated.resources.age_group
import playbook.composeapp.generated.resources.cancel
import playbook.composeapp.generated.resources.edit_game
import playbook.composeapp.generated.resources.field_cannot_be_empty
import playbook.composeapp.generated.resources.location
import playbook.composeapp.generated.resources.long_description
import playbook.composeapp.generated.resources.name
import playbook.composeapp.generated.resources.name_already_exists
import playbook.composeapp.generated.resources.number_of_players
import playbook.composeapp.generated.resources.rating
import playbook.composeapp.generated.resources.save
import playbook.composeapp.generated.resources.short_description
import playbook.composeapp.generated.resources.star
import playbook.composeapp.generated.resources.supplies
import playbook.composeapp.generated.resources.time

/**
 * Uj jatek letrehozasat segito dialogus
 * @param existingGames a mar letezo jatekok listaja
 * @param defaultGame az uj jatek alapertelmezett ertekei
 * @param onDismiss a dialogus bezarasa
 * @param onSave a mentes
 */
@Composable
fun AddGameDialog(
    existingGames: List<Game>,
    defaultGame: Game = Game(
        name = "",
        shortDescription = "",
        longDescription = "",
        supplies = "",
        ageGroup = listOf(AGEGROUP.KIDS),
        location = listOf(LOCATION.OUTDOOR),
        time = listOf(TIME.SHORT),
        numberOfPlayers = listOf(NUMBEROFPLAYERS.SMALL),
        rating = 1,
        ratingNumber = 1,
        isRatinged = false,
        liked = false
    ),
    isEdit: Boolean = false,
    onDismiss: () -> Unit,
    onSave: (Game) -> Unit
) {

    var name by remember { mutableStateOf(defaultGame.name) }
    var shortDescription by remember { mutableStateOf(defaultGame.shortDescription) }
    var longDescription by remember { mutableStateOf(defaultGame.longDescription) }
    var supplies by remember { mutableStateOf(defaultGame.supplies) }
    var rating by remember { mutableIntStateOf(defaultGame.rating) }
    var ratingNumber by remember { mutableIntStateOf(defaultGame.ratingNumber) }

    var selectedAgeGroups by remember { mutableStateOf(defaultGame.ageGroup) }
    var selectedLocations by remember { mutableStateOf(defaultGame.location) }
    var selectedTimes by remember { mutableStateOf(defaultGame.time) }
    var selectedPlayers by remember { mutableStateOf(defaultGame.numberOfPlayers) }

    val nameAlreadyExists = (!isEdit && existingGames.any { it.name.equals(name.trim(), ignoreCase = true) })
            || (isEdit && name != defaultGame.name && existingGames.any { it.name.equals(name.trim(), ignoreCase = true) })
    val nameAlreadyExistsError = nameAlreadyExists && name.isNotBlank()
    val nameError = name.isBlank()
    val shortDescriptionError = shortDescription.isBlank()
    val longDescriptionError = longDescription.isBlank()
    val ageGroupError = selectedAgeGroups.isEmpty()
    val locationError = selectedLocations.isEmpty()
    val timeError = selectedTimes.isEmpty()
    val numberOfPlayersError = selectedPlayers.isEmpty()


    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = if(isEdit) stringResource(Res.string.edit_game)
                            else stringResource(Res.string.add_new_game),
                    style = MaterialTheme.typography.titleLarge
                )

                // Nev
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(Res.string.name)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = nameAlreadyExistsError || nameError,
                    supportingText = {
                        if (nameAlreadyExistsError) {
                            Text(
                                text = stringResource(Res.string.name_already_exists),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        if (nameError) {
                            Text(
                                text = stringResource(Res.string.field_cannot_be_empty),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    })

                // Rovid leiras
                OutlinedTextField(
                    value = shortDescription,
                    onValueChange = { shortDescription = it },
                    label = { Text(stringResource(Res.string.short_description)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = shortDescriptionError,
                    supportingText = {
                        if (shortDescriptionError) {
                            Text(
                                text = stringResource(Res.string.field_cannot_be_empty),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                // Hosszu leiras
                OutlinedTextField(
                    value = longDescription,
                    onValueChange = { longDescription = it },
                    label = { Text(stringResource(Res.string.long_description)) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                    isError = longDescriptionError,
                    supportingText = {
                        if (longDescriptionError) {
                            Text(
                                text = stringResource(Res.string.field_cannot_be_empty),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                // Kellekek
                OutlinedTextField(
                    value = supplies,
                    onValueChange = { supplies = it },
                    label = { Text(stringResource(Res.string.supplies)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Korosztaly
                Text(stringResource(Res.string.age_group), fontWeight = FontWeight.Bold)
                Column {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AGEGROUP.entries.forEach { option ->
                            FilterChip(
                                selected = option in selectedAgeGroups,
                                onClick = {
                                    selectedAgeGroups = if (option in selectedAgeGroups)
                                        selectedAgeGroups - option
                                    else
                                        selectedAgeGroups + option
                                },
                                label = { Text(option.toDisplayString()) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }

                    if (ageGroupError) {
                        Text(
                            text = stringResource(Res.string.field_cannot_be_empty),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                // Helyszin
                Text(stringResource(Res.string.location), fontWeight = FontWeight.Bold)
                Column {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        LOCATION.entries.forEach { option ->
                            FilterChip(
                                selected = option in selectedLocations,
                                onClick = {
                                    selectedLocations = if (option in selectedLocations)
                                        selectedLocations - option
                                    else
                                        selectedLocations + option
                                },
                                label = { Text(option.toDisplayString()) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                    if (locationError) {
                        Text(
                            text = stringResource(Res.string.field_cannot_be_empty),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                // Idotartam
                Text(stringResource(Res.string.time), fontWeight = FontWeight.Bold)
                Column {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TIME.entries.forEach { option ->
                            FilterChip(
                                selected = option in selectedTimes,
                                onClick = {
                                    selectedTimes = if (option in selectedTimes)
                                        selectedTimes - option
                                    else
                                        selectedTimes + option
                                },
                                label = { Text(option.toDisplayString()) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                    if (timeError) {
                        Text(
                            text = stringResource(Res.string.field_cannot_be_empty),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                // Jatekosok szama
                Text(stringResource(Res.string.number_of_players), fontWeight = FontWeight.Bold)
                Column {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        NUMBEROFPLAYERS.entries.forEach { option ->
                            FilterChip(
                                selected = option in selectedPlayers,
                                onClick = {
                                    selectedPlayers = if (option in selectedPlayers)
                                        selectedPlayers - option
                                    else
                                        selectedPlayers + option
                                },
                                label = { Text(option.toDisplayString()) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                    if (numberOfPlayersError) {
                        Text(
                            text = stringResource(Res.string.field_cannot_be_empty),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                if (!isEdit) {
                    // ertekeles
                    Text(stringResource(Res.string.rating), fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        for (i in 1..5) {
                            val iconTint =
                                if (i <= rating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "$i " + stringResource(Res.string.star),
                                tint = iconTint,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable { rating = i }
                            )
                        }
                    }
                }

                // Gombok
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(Res.string.cancel))
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val newGame = Game(
                                name = name,
                                shortDescription = shortDescription,
                                longDescription = longDescription,
                                supplies = supplies,
                                ageGroup = selectedAgeGroups.ifEmpty { listOf(AGEGROUP.KIDS) },
                                location = selectedLocations.ifEmpty { listOf(LOCATION.OUTDOOR) },
                                time = selectedTimes.ifEmpty { listOf(TIME.SHORT) },
                                numberOfPlayers = selectedPlayers.ifEmpty { listOf(NUMBEROFPLAYERS.SMALL) },
                                rating = rating,
                                ratingNumber = ratingNumber,
                                isRatinged = defaultGame.isRatinged,
                                liked = false
                            )
                            onSave(newGame)
                            onDismiss()
                        },
                        enabled = !nameError &&
                                !nameAlreadyExists &&
                                !shortDescriptionError &&
                                !longDescriptionError &&
                                !ageGroupError &&
                                !locationError &&
                                !timeError &&
                                !numberOfPlayersError
                    ) {
                        Text(stringResource(Res.string.save))
                    }
                }
            }
        }
    }
}