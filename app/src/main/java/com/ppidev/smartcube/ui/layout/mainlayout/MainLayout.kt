package com.ppidev.smartcube.ui.layout.mainlayout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ppidev.smartcube.ui.BottomAppBar
import com.ppidev.smartcube.ui.NavigationApp
import com.ppidev.smartcube.ui.NavigationItem
import com.ppidev.smartcube.ui.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout() {
    val navController = rememberNavController()

    val items = listOf(
        NavigationItem(
            title = "Notifications",
            unselectedIcon = Icons.Outlined.Notifications,
            selectedIcon = Icons.Filled.Notifications,
            hasNews = false,
            badgeCount = null,
            screen = Screen.Notifications
        ),
        NavigationItem(
            title = "Dashboard",
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
            hasNews = false,
            badgeCount = null,
            screen = Screen.Dashboard
        ),
        NavigationItem(
            title = "Dashboard",
            unselectedIcon = Icons.Outlined.Person,
            selectedIcon = Icons.Filled.Person,
            hasNews = false,
            badgeCount = null,
            screen = Screen.Profile
        )
    )

    val routes = listOf(
        Screen.Notifications.screenRoute,
        Screen.Profile.screenRoute,
        Screen.Dashboard.screenRoute
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold (
            bottomBar = {
                if (navController.currentBackStackEntryAsState().value?.destination?.route in routes) {
                    BottomAppBar(navController = navController, listNavigationItem = items)
                }
            }
        ){
            Box {
                NavigationApp(navController)
            }
        }
    }
}
