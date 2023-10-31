package com.ppidev.smartcube.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ppidev.smartcube.ui.Screen

@Composable
fun SplashScreenCustom(
    navController: NavHostController,
    state: SplashState,
) {
    val degrees = remember { Animatable(0f) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Splash Screen")
    }

    LaunchedEffect(key1 = state.isAuthenticated) {
        degrees.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1800,
                delayMillis = 200
            )
        )

        navController.popBackStack()

        if (state.isAuthenticated) {
            navController.navigate(Screen.Dashboard.screenRoute)
        } else {
            navController.navigate(Screen.Login.screenRoute)
        }
    }
}