package com.ppidev.smartcube.presentation.splash

sealed class SplashEvent {
    data class ToDashboardScreen(
        val callback: () -> Unit
    ): SplashEvent()

    data class  ToLoginScreen(
        val callback: () -> Unit
    ): SplashEvent()
}