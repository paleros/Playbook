package com.peros.playbook.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.stringResource
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.cancel
import playbook.composeapp.generated.resources.rating
import playbook.composeapp.generated.resources.save

/**
 * Egy jatek ertekelesehez szukseges dialogus
 * @param onSave a jatek ertekelese
 * @param onDismiss a dialogus bezarasa
 */
@Composable
fun RatingDialog(
    onSave: (Double) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedRating by remember { mutableDoubleStateOf(1.0) }

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
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(Res.string.rating),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // Csillagok
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in 1..5) {
                        val tint = if (i <= selectedRating) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            tint = tint,
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { selectedRating = i.toDouble() }
                        )
                    }
                }

                // Gombok
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text(stringResource(Res.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onSave(selectedRating)
                        onDismiss()
                    }) {
                        Text(stringResource(Res.string.save))
                    }
                }
            }
        }
    }
}