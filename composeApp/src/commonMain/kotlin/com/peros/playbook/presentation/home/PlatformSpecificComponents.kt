package com.peros.playbook.presentation.home

import androidx.compose.runtime.Composable

/**
 * Platform specifikus menuelemek
 */
@Composable
expect fun PlatformSpecificDrawerItems( onClick: () -> Unit)