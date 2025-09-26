package com.peros.playbook.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.LOCATION
import com.peros.playbook.game.NUMBEROFPLAYERS
import com.peros.playbook.game.TIME
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.age_group
import playbook.composeapp.generated.resources.applying_filters
import playbook.composeapp.generated.resources.cancel
import playbook.composeapp.generated.resources.favorites_only
import playbook.composeapp.generated.resources.filter_settings
import playbook.composeapp.generated.resources.location
import playbook.composeapp.generated.resources.no_supplies_needed
import playbook.composeapp.generated.resources.number_of_players
import playbook.composeapp.generated.resources.time

/**
 * A szuresi beallitasokat tartalmazo dialogus
 * @param filterState a jelenlegi szuresi beallitasok
 * @param onDismiss a dialogus bezarasa
 * @param onApply a szuresi beallitasok alkalmazasa
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterDialog(
    filterState: FilterState,
    onDismiss: () -> Unit,
    onApply: (FilterState) -> Unit
) {
    var selectedPlayers by rememberSaveable { mutableStateOf(filterState.players) }
    var selectedTime by rememberSaveable { mutableStateOf(filterState.time) }
    var selectedAgeGroup by rememberSaveable { mutableStateOf(filterState.age) }
    var selectedLocation by rememberSaveable { mutableStateOf(filterState.location) }
    var noSupplies by rememberSaveable { mutableStateOf(filterState.noSupplies) }
    var onlyFavorites by rememberSaveable { mutableStateOf(filterState.onlyFavorites) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(Res.string.filter_settings)) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(stringResource(Res.string.number_of_players), fontWeight = FontWeight.Bold)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(NUMBEROFPLAYERS.SMALL,
                        NUMBEROFPLAYERS.MEDIUM,
                        NUMBEROFPLAYERS.LARGE,
                        NUMBEROFPLAYERS.HUGE).forEach { option ->
                        FilterChip(
                            selected = option in selectedPlayers,
                            onClick = {
                                selectedPlayers = if (option in selectedPlayers)
                                    selectedPlayers - option else selectedPlayers + option
                            },
                            label = { Text(option.toDisplayString()) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }

                Text(stringResource(Res.string.time), fontWeight = FontWeight.Bold)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(TIME.SHORT,
                        TIME.MEDIUM,
                        TIME.LONG).forEach { option ->
                        FilterChip(
                            selected = option in selectedTime,
                            onClick = {
                                selectedTime = if (option in selectedTime)
                                    selectedTime - option else selectedTime + option
                            },
                            label = { Text(option.toDisplayString()) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }

                Text(stringResource(Res.string.age_group), fontWeight = FontWeight.Bold)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(AGEGROUP.KIDS,
                        AGEGROUP.PRETEENS,
                        AGEGROUP.TEENS,
                        AGEGROUP.ADULTS).forEach { option ->
                        FilterChip(
                            selected = option in selectedAgeGroup,
                            onClick = {
                                selectedAgeGroup = if (option in selectedAgeGroup)
                                    selectedAgeGroup - option else selectedAgeGroup + option
                            },
                            label = { Text(option.toDisplayString()) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }

                Text(stringResource(Res.string.location), fontWeight = FontWeight.Bold)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(LOCATION.INDOOR,
                        LOCATION.OUTDOOR).forEach { option ->
                        FilterChip(
                            selected = option in selectedLocation,
                            onClick = {
                                selectedLocation = if (option in selectedLocation)
                                    selectedLocation - option else selectedLocation + option
                            },
                            label = { Text(option.toDisplayString()) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = noSupplies,
                        onCheckedChange = { noSupplies = it }
                    )
                    Text(stringResource(Res.string.no_supplies_needed))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = onlyFavorites,
                        onCheckedChange = { onlyFavorites = it }
                    )
                    Text(stringResource(Res.string.favorites_only))
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onApply(
                    FilterState(
                        players = selectedPlayers,
                        time = selectedTime,
                        age = selectedAgeGroup,
                        location = selectedLocation,
                        noSupplies = noSupplies,
                        onlyFavorites = onlyFavorites
                    )
                )
                onDismiss()
            }) {
                Text(stringResource(Res.string.applying_filters))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(stringResource(Res.string.cancel))
            }
        }
    )
}

/**
 * A szuresi beallitasokat tartalmazo adatosztaly
 * @param players a kivalasztott jatekosok szama
 * @param time a kivalasztott jatekido
 * @param age a kivalasztott korcsoport
 * @param location a kivalasztott helyszin
 * @param noSupplies ha true, csak olyan jatekokat mutat, amikhez nem kellenek eszkozok
 * @param onlyFavorites ha true, csak a kedvencek jelennek meg
 */
data class FilterState(
    val players: Set<NUMBEROFPLAYERS> = emptySet(),
    val time: Set<TIME> = emptySet(),
    val age: Set<AGEGROUP> = emptySet(),
    val location: Set<LOCATION> = emptySet(),
    val noSupplies: Boolean = false,
    val onlyFavorites: Boolean = false
)

/**
 * A szuro beallitasanak dialogusanak elonezete
 */
@Preview
@Composable
fun FilterDialogPreview() {
    FilterDialog(FilterState() ,onDismiss = {}, onApply = {})
}