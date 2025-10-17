package com.peros.playbook.presentation.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.peros.playbook.database.FilterState
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
import playbook.composeapp.generated.resources.minimum_rating
import playbook.composeapp.generated.resources.no_supplies_needed
import playbook.composeapp.generated.resources.number_of_players
import playbook.composeapp.generated.resources.star
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
    var selectedRating by rememberSaveable { mutableIntStateOf(filterState.minRating) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(stringResource(Res.string.filter_settings), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

                Text(stringResource(Res.string.number_of_players), fontWeight = FontWeight.Bold)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(NUMBEROFPLAYERS.SMALL, NUMBEROFPLAYERS.MEDIUM, NUMBEROFPLAYERS.LARGE, NUMBEROFPLAYERS.HUGE).forEach { option ->
                        FilterChip(
                            selected = option in selectedPlayers,
                            onClick = { selectedPlayers = if (option in selectedPlayers) selectedPlayers - option else selectedPlayers + option },
                            label = { Text(option.toDisplayString()) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }

                Text(stringResource(Res.string.time), fontWeight = FontWeight.Bold)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(TIME.SHORT, TIME.MEDIUM, TIME.LONG).forEach { option ->
                        FilterChip(
                            selected = option in selectedTime,
                            onClick = { selectedTime = if (option in selectedTime) selectedTime - option else selectedTime + option },
                            label = { Text(option.toDisplayString()) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }

                Text(stringResource(Res.string.age_group), fontWeight = FontWeight.Bold)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(AGEGROUP.KIDS, AGEGROUP.PRETEENS, AGEGROUP.TEENS, AGEGROUP.ADULTS).forEach { option ->
                        FilterChip(
                            selected = option in selectedAgeGroup,
                            onClick = { selectedAgeGroup = if (option in selectedAgeGroup) selectedAgeGroup - option else selectedAgeGroup + option },
                            label = { Text(option.toDisplayString()) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }

                Text(stringResource(Res.string.location), fontWeight = FontWeight.Bold)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(LOCATION.INDOOR, LOCATION.OUTDOOR).forEach { option ->
                        FilterChip(
                            selected = option in selectedLocation,
                            onClick = { selectedLocation = if (option in selectedLocation) selectedLocation - option else selectedLocation + option },
                            label = { Text(option.toDisplayString()) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = noSupplies, onCheckedChange = { noSupplies = it })
                    Text(stringResource(Res.string.no_supplies_needed))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = onlyFavorites, onCheckedChange = { onlyFavorites = it })
                    Text(stringResource(Res.string.favorites_only))
                }

                Text(stringResource(Res.string.minimum_rating), fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    for (i in 1..5) {
                        val iconTint = if (i <= selectedRating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "$i " + stringResource(Res.string.star),
                            tint = iconTint,
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { selectedRating = i }
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(onClick = onDismiss) { Text(stringResource(Res.string.cancel)) }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onApply(
                            FilterState(
                                players = selectedPlayers,
                                time = selectedTime,
                                age = selectedAgeGroup,
                                location = selectedLocation,
                                noSupplies = noSupplies,
                                onlyFavorites = onlyFavorites,
                                minRating = selectedRating
                            )
                        )
                        onDismiss()
                    }) {
                        Text(stringResource(Res.string.applying_filters))
                    }
                }
            }
        }
    }
}

/**
 * A szuro beallitasanak dialogusanak elonezete
 */
@Preview
@Composable
fun FilterDialogPreview() {
    FilterDialog(FilterState(),onDismiss = {}, onApply = {})
}