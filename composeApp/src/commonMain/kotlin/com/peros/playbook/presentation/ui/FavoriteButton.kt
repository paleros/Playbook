package com.peros.playbook.presentation.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import com.peros.playbook.theme.BaseWhite
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon

/**
 * Kedvenc gomb, amely megjeleniti, hogy egy elem kedvenc-e vagy sem.
 * @param isInitiallyFavorite Kezdeti allapot (kedvenc vagy sem).
 * @param defaultColor A gomb szine.
 * @param onFavoriteChange Callback, amely akkor hivodik meg, amikor a kedvenc allapot valtozik.
 */
@Composable
fun FavoriteButton(
    isInitiallyFavorite: Boolean = false,
    defaultColor: Color = BaseWhite,
    onFavoriteChange: (Boolean) -> Unit = {}
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
            tint = defaultColor,
            modifier = Modifier.size(32.dp)
        )
    }
}