package com.ppidev.smartcube.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.R
import com.ppidev.smartcube.ui.Screen

@Composable
fun SplashScreenCustom(
    state: SplashState,
    onEvent: (event: SplashEvent) -> Unit,
    navController: NavHostController
) {
    val degrees = remember { Animatable(0f) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(118.dp)
            )

            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Smartcube",
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }

    LaunchedEffect(key1 = state.isAuth) {
        degrees.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1800,
                delayMillis = 200
            )
        )

        state.isAuth.collect {
            when (it) {
                true -> {
                    onEvent(SplashEvent.ToDashboardScreen {
                        navController.navigate(Screen.Dashboard.screenRoute) {
                            popUpTo(Screen.Splash.screenRoute) {
                                inclusive = true
                            }
                        }
                    })
                }

                false -> {
                    onEvent(SplashEvent.ToLoginScreen {
                        navController.navigate(Screen.Login.screenRoute) {
                            popUpTo(Screen.Splash.screenRoute) {
                                inclusive = true
                            }
                        }
                    })
                }
            }
        }

    }
}