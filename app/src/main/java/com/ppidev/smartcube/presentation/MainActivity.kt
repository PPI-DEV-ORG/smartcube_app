package com.ppidev.smartcube.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.ppidev.smartcube.ui.layout.mainlayout.MainLayout
import com.ppidev.smartcube.ui.layout.mainlayout.MainLayoutViewModel
import com.ppidev.smartcube.ui.theme.SmartcubeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartcubeAppTheme {
                val mainLayoutViewModel = hiltViewModel<MainLayoutViewModel>()

                MainLayout(mainLayoutViewModel)
            }
        }
    }
}