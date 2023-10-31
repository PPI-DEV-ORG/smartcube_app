package com.ppidev.smartcube.ui.layout.mainlayout

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ppidev.smartcube.di.NavigationService
import com.ppidev.smartcube.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainLayoutViewModel @Inject constructor(
    private val navigationService: NavigationService,
) : ViewModel() {
    private val routes = listOf(
        Screen.Notifications.screenRoute,
        Screen.Dashboard.screenRoute,
        Screen.Profile.screenRoute
    )

    val navHostController = navigationService.navController

    val shouldShowBottomBar: Boolean
        @Composable get() =
            navigationService.navController.currentBackStackEntryAsState().value?.destination?.route in routes
}

//@Composable
//fun rememberNavHostControllerApp(
//    navHostController: NavigationService
//) = remember(navHostController) {
//    NavHostControllerApp(navHostController)
//}

//@Stable
//data class NavHostControllerApp @Inject constructor(
//    val navigationService: NavigationService
//) {
//    private val routes = listOf(
//        Screen.Notifications.screenRoute,
//        Screen.Dashboard.screenRoute,
//        Screen.Profile.screenRoute
//    )
//    val shouldShowBottomBar: Boolean
//        @Composable get() =
//            navigationService.navController.currentBackStackEntryAsState().value?.destination?.route in routes
//}