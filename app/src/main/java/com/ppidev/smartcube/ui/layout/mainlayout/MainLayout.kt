package com.ppidev.smartcube.ui.layout.mainlayout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ppidev.smartcube.ui.BottomAppBar
import com.ppidev.smartcube.ui.NavigationApp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(viewModel: MainLayoutViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                if (viewModel.shouldShowBottomBar) {
                    BottomAppBar(viewModel.navHostController)
                }
            }
        ) {
            Box {
                NavigationApp(viewModel.navHostController)
            }
        }
    }
}
