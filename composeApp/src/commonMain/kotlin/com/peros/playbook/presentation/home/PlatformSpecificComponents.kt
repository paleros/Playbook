package com.peros.playbook.presentation.home

import androidx.compose.runtime.Composable

/**
 * Platform specifikus menuelemek
 * @param onClick a menuelemre kattintas esemeny
 * @param onNoInternet internetkapcsolat hianya esemeny
 * @param isNetworkAvailable jelzi, hogy van-e elerheto halozat
 */
@Composable
expect fun PlatformSpecificDrawerItems( onClick: () -> Unit, onNoInternet: () -> Unit, isNetworkAvailable: Boolean)