package com.ppidev.smartcube

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ppidev.smartcube.presentation.dashboard.DashboardScreen
import com.ppidev.smartcube.presentation.dashboard.DashboardViewModel
import com.ppidev.smartcube.presentation.profile.ProfileScreen
import com.ppidev.smartcube.presentation.setting.SettingScreen

@Composable
fun NavigationApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Dashboard.screenRoute) {
        composable(BottomNavItem.Dashboard.screenRoute) {
            DashboardScreen(DashboardViewModel())
        }
        composable(BottomNavItem.Profile.screenRoute) {
            ProfileScreen()
        }
        composable(BottomNavItem.Setting.screenRoute) {
            SettingScreen()
        }
//        navigation(
//            startDestination = "login",
//            route = "auth"
//        ) {
//            composable("login") {
//
//            }
//            composable("register") {
//
//            }
//            composable("forgot_password") {
//
//            }
//        }
    }
}