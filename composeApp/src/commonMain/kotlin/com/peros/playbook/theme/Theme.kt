package com.peros.playbook.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = AccentOrange,
    secondary = AccentLightOrange,
    tertiary = AccentYellowOrange,
    background = BaseTextBlack,
    surface = BaseTextGray,
    onPrimary = BaseWhite,
    onSecondary = BaseWhite,
    onBackground = BaseWhite,
    onSurface = BaseWhite,
)

private val LightColorScheme = lightColorScheme(
    primary = AccentOrange,
    secondary = AccentLightOrange,
    tertiary = AccentYellowOrange,
    background = BaseWhite,
    surface = BaseWhite,
    onPrimary = BaseWhite,
    onSecondary = BaseWhite,
    onBackground = BaseTextGray,
    onSurface = BaseTextBlack
)

/**
 * Az alkalmazas szineit es tipografiat beallito komponens.
 */
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}