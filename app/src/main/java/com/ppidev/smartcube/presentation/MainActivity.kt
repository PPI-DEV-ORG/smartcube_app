package com.ppidev.smartcube.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.ppidev.smartcube.ui.layout.mainlayout.MainLayout
import com.ppidev.smartcube.ui.theme.SmartcubeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SmartcubeAppTheme {
                Box(Modifier.safeDrawingPadding()) {
                    MainLayout()
                }
            }
        }
    }
}