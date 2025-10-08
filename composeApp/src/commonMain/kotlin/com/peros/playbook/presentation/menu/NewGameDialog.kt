package com.peros.playbook.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.Game
import com.peros.playbook.game.LOCATION
import com.peros.playbook.game.NUMBEROFPLAYERS
import com.peros.playbook.game.TIME
import com.peros.playbook.presentation.ui.DropdownSelector
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.add_new_game
import playbook.composeapp.generated.resources.age_group
import playbook.composeapp.generated.resources.cancel
import playbook.composeapp.generated.resources.location
import playbook.composeapp.generated.resources.long_description
import playbook.composeapp.generated.resources.name
import playbook.composeapp.generated.resources.number_of_players
import playbook.composeapp.generated.resources.save
import playbook.composeapp.generated.resources.short_description
import playbook.composeapp.generated.resources.supplies
import playbook.composeapp.generated.resources.time

/**
 * Uj jatek letrehozasat segito dialogus
 * @param onDismiss a dialogus bezarasa
 * @param onSave a mentes
 */
@Composable
fun AddGameDialog(
    onDismiss: () -> Unit,
    onSave: (Game) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var shortDescription by remember { mutableStateOf("") }
    var longDescription by remember { mutableStateOf("") }
    var supplies by remember { mutableStateOf("") }

    var selectedAgeGroup by remember { mutableStateOf(AGEGROUP.KIDS) }
    var selectedLocation by remember { mutableStateOf(LOCATION.OUTDOOR) }
    var selectedTime by remember { mutableStateOf(TIME.SHORT) }
    var selectedPlayers by remember { mutableStateOf(NUMBEROFPLAYERS.SMALL) }
//TODO tobb elemet is ki lehessen valasztani
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
                    text = stringResource(Res.string.add_new_game),
                    style = MaterialTheme.typography.titleLarge
                )

                // Nev
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(Res.string.name)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Rovid leiras
                OutlinedTextField(
                    value = shortDescription,
                    onValueChange = { shortDescription = it },
                    label = { Text(stringResource(Res.string.short_description)) },
                    modifier = Modifier.fillMaxWidth()
                )

                // Hosszu leiras
                OutlinedTextField(
                    value = longDescription,
                    onValueChange = { longDescription = it },
                    label = { Text(stringResource(Res.string.long_description)) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp)
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
                DropdownSelector(
                    label = stringResource(Res.string.age_group),
                    options = AGEGROUP.entries.toTypedArray(),
                    selected = selectedAgeGroup,
                    onSelect = { selectedAgeGroup = it }
                )
                // Helyszin
                DropdownSelector(
                    label = stringResource(Res.string.location),
                    options = LOCATION.entries.toTypedArray(),
                    selected = selectedLocation,
                    onSelect = { selectedLocation = it }
                )
                // Idotartam
                DropdownSelector(
                    label = stringResource(Res.string.time),
                    options = TIME.entries.toTypedArray(),
                    selected = selectedTime,
                    onSelect = { selectedTime = it }
                )
                // Jatekosok szama
                DropdownSelector(
                    label = stringResource(Res.string.number_of_players),
                    options = NUMBEROFPLAYERS.entries.toTypedArray(),
                    selected = selectedPlayers,
                    onSelect = { selectedPlayers = it }
                )

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
                                ageGroup = listOf(selectedAgeGroup),
                                location = listOf(selectedLocation),
                                time = listOf(selectedTime),
                                numberOfPlayers = listOf(selectedPlayers),
                                liked = false
                            )
                            onSave(newGame)
                            onDismiss()
                        },
                        enabled = name.isNotBlank()
                    ) {
                        Text(stringResource(Res.string.save))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddGameDialogPreview() {
    AddGameDialog(onDismiss = {}, onSave = {})
}