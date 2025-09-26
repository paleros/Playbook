package com.peros.playbook.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.about_created_by
import playbook.composeapp.generated.resources.about_description
import playbook.composeapp.generated.resources.ok
import playbook.composeapp.generated.resources.playbook
import playbook.composeapp.generated.resources.version
import playbook.composeapp.generated.resources.version_number

/**
 * Az alkalmazasrol szolo dialogus
 * @param onDismiss a dialogus bezarasa
 */
@Composable
fun AboutDialog(onDismiss: () -> Unit) {

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Extension,
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                )

                Text(
                    text = stringResource(Res.string.playbook),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(Res.string.version) + ": " +
                            stringResource(Res.string.version_number),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(Res.string.about_description),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = stringResource(Res.string.about_created_by),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(Res.string.ok))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AboutPreview() {
    AboutDialog(onDismiss = {})
}