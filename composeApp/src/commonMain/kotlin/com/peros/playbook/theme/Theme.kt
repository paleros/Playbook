package com.peros.playbook.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    /*primary = AccentOrange,
    secondary = AccentLightOrange,
    tertiary = AccentYellowOrange,
    background = AccentLightOrange,
    surface = BaseTextGray,
    onPrimary = BaseTextGray,
    onSecondary = BaseTextGray,
    onBackground = BaseTextGray,
    onSurface = BaseWhite,*/
    primary = purple,
    secondary = green,
    tertiary = red,
    background = black,
    surface = black,
    onPrimary = white,
    onSecondary = white,
    onBackground = white,
    onSurface = white
)

private val LightColorScheme = lightColorScheme(
    /*primary = AccentOrange,
    secondary = AccentLightOrange,
    tertiary = AccentYellowOrange,
    background = AccentLightOrange,
    surface = BaseWhite,
    onPrimary = BaseWhite,
    onSecondary = BaseWhite,
    onBackground = BaseWhite,
    onSurface = BaseTextGray*/
    primary = purple,
    secondary = green,
    tertiary = red,
    background = white,
    surface = white,
    onPrimary = white,
    onSecondary = white,
    onBackground = black,
    onSurface = black
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