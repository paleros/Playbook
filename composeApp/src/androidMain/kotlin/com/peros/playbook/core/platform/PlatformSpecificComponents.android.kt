package com.peros.playbook.core.platform

import androidx.compose.runtime.Composable

/**
 * A csak az androidhoz tartozo menuelemek
 * @param onClick a menuelemre kattintas esemeny
 * @param onNoInternet internetkapcsolat hianya esemeny
 * @param isNetworkAvailable jelzi, hogy van-e elerheto halozat
 */
@Composable
actual fun PlatformSpecificDrawerItems( onClick: () -> Unit, onNoInternet: () -> Unit, isNetworkAvailable: Boolean) {
}