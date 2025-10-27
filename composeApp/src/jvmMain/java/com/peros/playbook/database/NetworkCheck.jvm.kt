package com.peros.playbook.database

import java.net.InetSocketAddress
import java.net.Socket

/**
 * Ellenorzi, hogy van-e elerheto halozat JVM platformon
 * @return true, ha van elerheto halozat, kulonben false
 */
actual fun isNetworkAvailable(): Boolean {
    return try {
        Socket().use { socket ->
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            true
        }
    } catch (_: Exception) {
        false
    }
}