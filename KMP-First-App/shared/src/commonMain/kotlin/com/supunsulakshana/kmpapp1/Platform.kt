package com.supunsulakshana.kmpapp1

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform