package com.peros.playbook

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform