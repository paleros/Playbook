package com.peros.playbook.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.peros.playbook.game.AGEGROUP
import com.peros.playbook.game.LOCATION
import com.peros.playbook.game.NUMBEROFPLAYERS
import com.peros.playbook.game.TIME

/**
 * Legordulo menut megjelenito komponens, amely enum tipusu opciokat kezel
 * @param label a mezo felirata
 * @param options a valaszthato opciok tombje
 * @param selected a jelenleg kivalasztott opcio
 * @param onSelect a kivalasztas esemenykezeloje
 */
@Composable
fun <T : Enum<T>> DropdownSelector(
    label: String,
    options: Array<T>,
    selected: T,
    onSelect: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selected.toDisplayStringSafe(),
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

/**
 * Biztonsagos toString fuggveny, amely kezeli a jatekhoz tartozo enumokat
 * @return a lokalizalt szoveg, vagy az enum neve
 */
@Composable
fun <T : Enum<T>> T.toDisplayStringSafe(): String {
    return when (this) {
        is AGEGROUP -> this.toDisplayString()
        is TIME -> this.toDisplayString()
        is NUMBEROFPLAYERS -> this.toDisplayString()
        is LOCATION -> this.toDisplayString()
        else -> this.toString()
    }
}