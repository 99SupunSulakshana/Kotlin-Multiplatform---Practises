package com.supunsulakshana.kmpapp1

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP-First-App",
    ) {
        App()
    }
}