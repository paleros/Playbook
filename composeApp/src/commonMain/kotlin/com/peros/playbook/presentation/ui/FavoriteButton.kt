package com.peros.playbook.presentation.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color

/**
 * Kedvenc gomb, amely megjeleniti, hogy egy elem kedvenc-e vagy sem.
 * @param isInitiallyFavorite Kezdeti allapot (kedvenc vagy sem).
 * @param onFavoriteChange Callback, amely akkor hivodik meg, amikor a kedvenc allapot valtozik.
 * @param color A gomb szine.
 */
@Composable
fun FavoriteButton(
    isInitiallyFavorite: Boolean = false,
    onFavoriteChange: (Boolean) -> Unit = {},
    color: Color = MaterialTheme.colorScheme.surface
) {
    var isFavorite by remember { mutableStateOf(isInitiallyFavorite) }

    IconButton(
        onClick = {
            isFavorite = !isFavorite
            onFavoriteChange(isFavorite)
        }
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = color
        )
    }
}