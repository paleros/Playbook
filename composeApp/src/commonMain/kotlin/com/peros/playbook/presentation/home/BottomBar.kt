package com.peros.playbook.presentation.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * A fo kepernyo also savigombjai
 * @param onSortClick a rendezogomb kattintas esemeny
 * @param onFilterClick a szurogomb kattintas esemeny
 * @param onRandomClick a random gomb kattintas esemeny
 * @param showIndicator megjelenik-e az aktiv filter jelzo
 */
@Composable
fun BottomBar(
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit,
    onRandomClick: () -> Unit,
    showIndicator: Boolean

) {

    var flipped by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        BottomAppBar(
            modifier = Modifier.height(64.dp),
            actions = {
                IconButton(onClick = {onSortClick()
                        flipped = !flipped }
                ) {
                    val rotation by animateFloatAsState(
                        targetValue = if (flipped) 180f else 0f,
                        animationSpec = tween(durationMillis = 300)
                    )

                    Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Sort",
                        Modifier.height(40.dp).width(40.dp).graphicsLayer { rotationZ = rotation })
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onFilterClick) {
                    Box {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter",
                            Modifier.height(40.dp).width(40.dp))
                        if (showIndicator) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-7).dp, y = (7).dp)
                                    .background(
                                        MaterialTheme.colorScheme.secondary,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
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
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(
                    imageVector = Icons.Default.Extension,
                    contentDescription = "Random",
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.height(40.dp).width(40.dp)
                )
            }
        }
    }
}

/**
 * Preview a BottomBar komponenshez
 */
/*@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(
        onSortClick = {},
        onFilterClick = {},
        onRandomClick = {},
    )
}*/