package com.peros.playbook.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A fo kepernyo also savigombjai
 * @param onSortClick a rendezogomb kattintas esemeny
 * @param onFilterClick a szurogomb kattintas esemeny
 * @param onRandomClick a random gomb kattintas esemeny
 */
@Composable
fun BottomBar(
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit,
    onRandomClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        BottomAppBar(
            modifier = Modifier.height(64.dp),
            actions = {
                IconButton(onClick = onSortClick) {
                    Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Sort",
                        Modifier.height(40.dp).width(40.dp))
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onFilterClick) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter",
                        Modifier.height(40.dp).width(40.dp))
                }
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-25).dp),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = onRandomClick,
                shape = CircleShape,
                modifier = Modifier
                    .height(64.dp)
                    .width(64.dp),
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.Default.Extension,
                    contentDescription = "Random",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.height(40.dp).width(40.dp)
                )
            }
        }
    }
}

/**
 * Preview a BottomBar komponenshez
 */
@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(
        onSortClick = {},
        onFilterClick = {},
        onRandomClick = {}
    )
}