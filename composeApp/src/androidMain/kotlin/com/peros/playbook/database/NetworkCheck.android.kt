package com.peros.playbook.database

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

lateinit var appContext: Context

/**
 * Az alkalmazas kontextusa
 * @param Context az alkalmazas kontextusa
 */
fun initNetworkUtils(context: Context) {
    appContext = context.applicationContext
}

/**
 * Ellenorzi, hogy van-e elerheto halozat Android platformon
 * @return true, ha van elerheto halozat, kulonben false
 */
actual fun isNetworkAvailable(): Boolean {
    if (!::appContext.isInitialized) return false

    val connectivityManager =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}