package com.ppidev.smartcube.ui

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.ppidev.smartcube.common.APP_URL
import com.ppidev.smartcube.common.CHANGE_PASSWORD_ARG
import com.ppidev.smartcube.common.NOTIFICATION_ARG
import com.ppidev.smartcube.presentation.dashboard.DashboardScreen
import com.ppidev.smartcube.presentation.dashboard.DashboardViewModel
import com.ppidev.smartcube.presentation.login.LoginScreen
import com.ppidev.smartcube.presentation.login.LoginViewModel
import com.ppidev.smartcube.presentation.notification.NotificationListScreen
import com.ppidev.smartcube.presentation.profile.ProfileScreen
import com.ppidev.smartcube.presentation.register.RegisterScreen
import com.ppidev.smartcube.presentation.register.RegisterViewModel
import com.ppidev.smartcube.presentation.reset_password.ResetPasswordScreen
import com.ppidev.smartcube.presentation.reset_password.ResetPasswordViewModel
import com.ppidev.smartcube.presentation.reset_password.change_password.ChangePasswordScreen
import com.ppidev.smartcube.presentation.reset_password.change_password.ChangePasswordViewModel
import com.ppidev.smartcube.presentation.splash.SplashScreenCustom
import com.ppidev.smartcube.presentation.splash.SplashViewModel
import com.ppidev.smartcube.presentation.verification.VerificationScreen
import com.ppidev.smartcube.presentation.verification.VerificationViewModel

@Composable
fun NavigationApp(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.screenRoute
    ) {
        composable(Screen.Splash.screenRoute) {
            val viewModel = hiltViewModel<SplashViewModel>()
            val state = viewModel.state
            SplashScreenCustom(
                state = state,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }
        composable(Screen.Login.screenRoute) {
            val viewModel = hiltViewModel<LoginViewModel>()
            val state = viewModel.state
            LoginScreen(
                state = state, onEvent = viewModel::onEvent, navHostController = navController
            )
        }
        composable(Screen.Register.screenRoute) {
            val viewModel = hiltViewModel<RegisterViewModel>()
            val state = viewModel.state
            RegisterScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navHostController = navController
            )
        }

        composable(
            route = Screen.Verification.screenRoute + "/{emailArg}",
            arguments = listOf(navArgument("emailArg") { type = NavType.StringType})
        ) {
            val viewModel = hiltViewModel<VerificationViewModel>()
            val state = viewModel.state

            val arguments = it.arguments

            arguments?.getString("emailArg")?.let { emailArg ->
                VerificationScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    email = emailArg,
                    navHostController = navController
                )
            }

        }

        composable(Screen.ResetPassword.screenRoute) {
            val viewModel = hiltViewModel<ResetPasswordViewModel>()
            val state = viewModel.state

            ResetPasswordScreen(
                state = state, onEvent = viewModel::onEvent, navHostController = navController
            )
        }

        composable(
            route = Screen.ChangePassword.screenRoute,
            arguments = listOf(navArgument(CHANGE_PASSWORD_ARG) { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "$APP_URL/$CHANGE_PASSWORD_ARG/{$CHANGE_PASSWORD_ARG}"
                action = Intent.ACTION_VIEW
            })
        ) {

            val viewModel = hiltViewModel<ChangePasswordViewModel>()
            val state = viewModel.state

            val arguments = it.arguments
            arguments?.getString(CHANGE_PASSWORD_ARG)?.let { token ->
                ChangePasswordScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    argToken = token,
                    navHostController = navController
                )
            }
        }
        composable(Screen.Dashboard.screenRoute) {
            val viewModel = hiltViewModel<DashboardViewModel>()
            val state = viewModel.state
            DashboardScreen(
                state = state, onEvent = viewModel::onEvent
            )
        }
        composable(Screen.Profile.screenRoute) {
            ProfileScreen()
        }
        composable(Screen.Notifications.screenRoute) {
            NotificationListScreen()
        }
        composable(
            route = Screen.DetailNotification.screenRoute,
            arguments = listOf(navArgument(NOTIFICATION_ARG) { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "$APP_URL/$NOTIFICATION_ARG/{$NOTIFICATION_ARG}"
            })
        ) {
            val arguments = it.arguments
            arguments?.getInt(NOTIFICATION_ARG)?.let { message ->
                Box {
                    Text(
                        "$message"
                    )
                }
            }
        }
    }
}

sealed class Screen(val screenRoute: String) {
    object Splash : Screen(screenRoute = "splash")
    object Login : Screen(screenRoute = "login")
    object Register : Screen(screenRoute = "register")

    object Verification : Screen(screenRoute = "verification")
    object ResetPassword : Screen(screenRoute = "resetPassword")
    object ChangePassword : Screen(screenRoute = "changePassword")
    object Dashboard : Screen(screenRoute = "dashboard")
    object Notifications : Screen(screenRoute = "Notifications")
    object DetailNotification : Screen(screenRoute = "notification/detail")
    object Profile : Screen(screenRoute = "profile")
}


data class NavigationItem(
    val title: String,
    var selectedIcon: ImageVector,
    var unselectedIcon: ImageVector,
    var hasNews: Boolean,
    var badgeCount: Int?,
    val screen: Screen
)