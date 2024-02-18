package com.ppidev.smartcube.presentation.splash

import kotlinx.coroutines.flow.Flow


data class SplashState(
    val isAuth: Flow<Boolean>
)