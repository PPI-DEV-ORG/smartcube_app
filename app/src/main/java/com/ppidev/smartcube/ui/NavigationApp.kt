package com.ppidev.smartcube.ui

import android.content.Intent
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
import com.ppidev.smartcube.common.DEVICE_PROCESS_ID
import com.ppidev.smartcube.common.EDGE_DEVICE_ID_ARG
import com.ppidev.smartcube.common.EDGE_SERVER_ACCESS_TOKEN
import com.ppidev.smartcube.common.EDGE_SERVER_ID_ARG
import com.ppidev.smartcube.common.NOTIFICATION_ARG
import com.ppidev.smartcube.presentation.dashboard.DashboardScreen
import com.ppidev.smartcube.presentation.dashboard.DashboardViewModel
import com.ppidev.smartcube.presentation.edge_device.detail.DetailEdgeDeviceScreen
import com.ppidev.smartcube.presentation.edge_device.detail.DetailEdgeDeviceViewModel
import com.ppidev.smartcube.presentation.edge_device.form_add.FormAddEdgeDeviceScreen
import com.ppidev.smartcube.presentation.edge_device.form_add.FormAddEdgeDeviceViewModel
import com.ppidev.smartcube.presentation.edge_device.list.ListEdgeDeviceScreen
import com.ppidev.smartcube.presentation.edge_device.list.ListEdgeDeviceViewModel
import com.ppidev.smartcube.presentation.edge_device.update.UpdateEdgeDeviceScreen
import com.ppidev.smartcube.presentation.edge_device.update.UpdateEdgeDeviceViewModel
import com.ppidev.smartcube.presentation.edge_server.detail.DetailEdgeServerScreen
import com.ppidev.smartcube.presentation.edge_server.detail.DetailEdgeServerViewModel
import com.ppidev.smartcube.presentation.edge_server.form_add.FormAddEdgeServerScreen
import com.ppidev.smartcube.presentation.edge_server.form_add.FormAddEdgeServerViewModel
import com.ppidev.smartcube.presentation.edge_server.list.ListEdgeServerScreen
import com.ppidev.smartcube.presentation.edge_server.list.ListEdgeServerViewModel
import com.ppidev.smartcube.presentation.login.LoginScreen
import com.ppidev.smartcube.presentation.login.LoginViewModel
import com.ppidev.smartcube.presentation.notification.NotificationListScreen
import com.ppidev.smartcube.presentation.notification.NotificationViewModel
import com.ppidev.smartcube.presentation.notification.notification_detail.NotificationDetailScreen
import com.ppidev.smartcube.presentation.notification.notification_detail.NotificationDetailViewModel
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
            arguments = listOf(navArgument("emailArg") { type = NavType.StringType })
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
                state = state, onEvent = viewModel::onEvent, navHostController = navController
            )
        }

        composable(Screen.Profile.screenRoute) {
            ProfileScreen()

        }

        composable(Screen.Notifications.screenRoute) {
            val viewModel = hiltViewModel<NotificationViewModel>()
            NotificationListScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                navHostController = navController
            )
        }
        composable(
            route = Screen.DetailNotification.screenRoute + "/{$NOTIFICATION_ARG}",
            arguments = listOf(navArgument(NOTIFICATION_ARG) { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "$APP_URL/$NOTIFICATION_ARG={$NOTIFICATION_ARG}"
            })
        ) {
            val arguments = it.arguments
            arguments?.getInt(NOTIFICATION_ARG)?.let { message ->
                val viewModel = hiltViewModel<NotificationDetailViewModel>()

                NotificationDetailScreen(
                    state = viewModel.state,
                    onEvent = viewModel::onEvent,
                    notificationId = message.toUInt(),
                    navHostController = navController
                )
            }
        }

        composable(
            route = Screen.FormAddEdgeServer.screenRoute
        ) {
            val viewModel = hiltViewModel<FormAddEdgeServerViewModel>()
            FormAddEdgeServerScreen(
                state = viewModel.state, onEvent = viewModel::onEvent,
                navHostController = navController
            )
        }

        composable(
            route = Screen.ListEdgeServer.screenRoute
        ) {
            val viewModel = hiltViewModel<ListEdgeServerViewModel>()
            ListEdgeServerScreen(
                state = viewModel.state,
                event = viewModel::onEvent,
                navHostController = navController
            )
        }

        composable(
            route = Screen.FormAddEdgeDevice.screenRoute + "/{${EDGE_SERVER_ID_ARG}}",
            arguments = listOf(navArgument(EDGE_SERVER_ID_ARG) { type = NavType.IntType }),
        ) {
            val viewModel = hiltViewModel<FormAddEdgeDeviceViewModel>()

            val arguments = it.arguments
            arguments?.getInt(EDGE_SERVER_ID_ARG)?.let { edgeServerId ->
                FormAddEdgeDeviceScreen(
                    state = viewModel.state,
                    onEvent = viewModel::onEvent,
                    edgeServerId = edgeServerId.toUInt(),
                    navHostController = navController
                )
            }
        }

        composable(
            route = Screen.DetailEdgeServer.screenRoute + "?${EDGE_SERVER_ID_ARG}={${EDGE_SERVER_ID_ARG}}&${EDGE_SERVER_ACCESS_TOKEN}={${EDGE_SERVER_ACCESS_TOKEN}}",
            arguments = listOf(
                navArgument(EDGE_SERVER_ID_ARG) { type = NavType.IntType },
                navArgument(EDGE_SERVER_ACCESS_TOKEN) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }),
        ) {

            val arguments = it.arguments
            val edgeServerAccessToken = arguments?.getString(EDGE_SERVER_ACCESS_TOKEN)

            arguments?.getInt(EDGE_SERVER_ID_ARG)?.let { edgeServerId ->
                val viewModel = hiltViewModel<DetailEdgeServerViewModel>()

                DetailEdgeServerScreen(
                    state = viewModel.state,
                    onEvent = viewModel::onEvent,
                    navHostController = navController,
                    edgeServerId = edgeServerId.toUInt(),
                    edgeServerAccessToken = edgeServerAccessToken
                )
            }
        }

        composable(
            route = Screen.DetailEdgeDevice.screenRoute + "/{$EDGE_SERVER_ID_ARG}/{$EDGE_DEVICE_ID_ARG}/{$DEVICE_PROCESS_ID}/{vendor}/{type}",
            arguments = listOf(
                navArgument(EDGE_SERVER_ID_ARG) { type = NavType.IntType },
                navArgument(EDGE_DEVICE_ID_ARG) { type = NavType.IntType },
                navArgument(DEVICE_PROCESS_ID) { type = NavType.IntType },
                navArgument("vendor") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType }
            )
        ) {
            val arguments = it.arguments
            val edgeServerId = arguments?.getInt(EDGE_SERVER_ID_ARG) ?: return@composable
            val edgeDeviceId = arguments.getInt(EDGE_DEVICE_ID_ARG) ?: return@composable
            val processId = arguments.getInt(DEVICE_PROCESS_ID) ?: return@composable
            val vendor = arguments.getString("vendor") ?: return@composable
            val type = arguments.getString("type") ?: return@composable

            val viewModel = hiltViewModel<DetailEdgeDeviceViewModel>()

            DetailEdgeDeviceScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                navHostController = navController,
                edgeDeviceId = edgeDeviceId.toUInt(),
                edgeServerId = edgeServerId.toUInt(),
                processId = processId,
                vendor = vendor,
                type = type
            )
        }

        composable(
            route = Screen.UpdateEdgeDevice.screenRoute + "/{${EDGE_SERVER_ID_ARG}}/{${EDGE_DEVICE_ID_ARG}}",
            arguments = listOf(
                navArgument(EDGE_SERVER_ID_ARG) { type = NavType.IntType },
                navArgument(EDGE_DEVICE_ID_ARG) { type = NavType.IntType },
            )
        ) {
            val arguments = it.arguments
            val edgeServerId = arguments?.getInt(EDGE_SERVER_ID_ARG)
            val edgeDeviceId = arguments?.getInt(EDGE_DEVICE_ID_ARG)

            if (edgeServerId == null || edgeDeviceId == null) {
                return@composable
            }

            val viewModel = hiltViewModel<UpdateEdgeDeviceViewModel>()

            UpdateEdgeDeviceScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                edgeServerId = edgeServerId.toUInt(),
                edgeDeviceId = edgeDeviceId.toUInt()
            )
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
    object Notifications : Screen(screenRoute = "notifications")
    object DetailNotification : Screen(screenRoute = "notification")
    object Profile : Screen(screenRoute = "profile")
    object FormAddEdgeServer : Screen(screenRoute = "addEdgeServer")
    object ListEdgeServer : Screen(screenRoute = "listEdgeServer")
    object ListEdgeDevices : Screen(screenRoute = "listEdgeDevices")
    object FormAddEdgeDevice : Screen(screenRoute = "addEdgeDevice")
    object DetailEdgeServer : Screen(screenRoute = "detailEdgeServer")
    object DetailEdgeDevice : Screen(screenRoute = "detailEdgeDevice")
    object UpdateEdgeDevice : Screen(screenRoute = "updateEdgeDevice")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(screenRoute)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}


data class NavigationItem(
    val title: String,
    var selectedIcon: ImageVector,
    var unselectedIcon: ImageVector,
    var hasNews: Boolean,
    var badgeCount: Int?,
    val screen: Screen
)