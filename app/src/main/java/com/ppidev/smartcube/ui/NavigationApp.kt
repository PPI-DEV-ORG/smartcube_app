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
import com.ppidev.smartcube.utils.APP_URL
import com.ppidev.smartcube.utils.CHANGE_PASSWORD_ARG
import com.ppidev.smartcube.utils.DEVICE_PROCESS_ID
import com.ppidev.smartcube.utils.EDGE_DEVICE_ID_ARG
import com.ppidev.smartcube.utils.EDGE_SERVER_ACCESS_TOKEN
import com.ppidev.smartcube.utils.EDGE_SERVER_ID_ARG
import com.ppidev.smartcube.utils.NOTIFICATION_ARG
import com.ppidev.smartcube.presentation.dashboard.DashboardScreen
import com.ppidev.smartcube.presentation.dashboard.DashboardViewModel
import com.ppidev.smartcube.presentation.edge_device.detail.camera.DetailEdgeDeviceScreen
import com.ppidev.smartcube.presentation.edge_device.detail.camera.DetailEdgeDeviceViewModel
import com.ppidev.smartcube.presentation.edge_device.detail.sensor.DetailEdgeDeviceSensorScreen
import com.ppidev.smartcube.presentation.edge_device.detail.sensor.DetailEdgeDeviceSensorViewModel
import com.ppidev.smartcube.presentation.edge_device.form_add.FormAddEdgeDeviceScreen
import com.ppidev.smartcube.presentation.edge_device.form_add.FormAddEdgeDeviceViewModel
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
import com.ppidev.smartcube.presentation.notification.list.NotificationListScreen
import com.ppidev.smartcube.presentation.notification.list.NotificationViewModel
import com.ppidev.smartcube.presentation.notification.detail.NotificationDetailScreen
import com.ppidev.smartcube.presentation.notification.detail.NotificationDetailViewModel
import com.ppidev.smartcube.presentation.profile.ProfileScreen
import com.ppidev.smartcube.presentation.profile.ProfileViewModel
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

            val viewModel = hiltViewModel<ProfileViewModel>()

            ProfileScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                navHostController = navController
            )

        }

        composable(
            Screen.Notifications.screenRoute,
            deepLinks = listOf(navDeepLink {
                uriPattern = "$APP_URL/${Screen.Notifications.screenRoute}"
            })
        ) {
            val viewModel = hiltViewModel<NotificationViewModel>()

            NotificationListScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                navHostController = navController
            )
        }
        composable(
            route = Screen.DetailNotification.screenRoute + "/{$NOTIFICATION_ARG}/{$EDGE_SERVER_ID_ARG}/{deviceType}",
            arguments = listOf(
                navArgument(NOTIFICATION_ARG) { type = NavType.IntType },
                navArgument(EDGE_SERVER_ID_ARG) { type = NavType.IntType },
                navArgument("deviceType") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink {
                uriPattern =
                    "$APP_URL/$NOTIFICATION_ARG={$NOTIFICATION_ARG}/$EDGE_SERVER_ID_ARG={$EDGE_SERVER_ID_ARG}/deviceType={deviceType}"
            })
        ) {
            val arguments = it.arguments
            val notificationId = arguments?.getInt(NOTIFICATION_ARG) ?: return@composable
            val serverId = arguments.getInt(EDGE_SERVER_ID_ARG) ?: return@composable
            val deviceType = arguments.getString("deviceType") ?: return@composable

            val viewModel = hiltViewModel<NotificationDetailViewModel>()

            NotificationDetailScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                notificationId = notificationId.toUInt(),
                edgeServerId = serverId.toUInt(),
                deviceType = deviceType,
                navHostController = navController
            )

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
            route = Screen.FormAddEdgeDevice.screenRoute + "/{$EDGE_SERVER_ID_ARG}",
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
            route = Screen.DetailEdgeServer.screenRoute + "?$EDGE_SERVER_ID_ARG={$EDGE_SERVER_ID_ARG}&$EDGE_SERVER_ACCESS_TOKEN={$EDGE_SERVER_ACCESS_TOKEN}",
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
            route = Screen.DetailEdgeDevice.screenRoute + "/{$EDGE_SERVER_ID_ARG}/{$EDGE_DEVICE_ID_ARG}/{$DEVICE_PROCESS_ID}/{mqttPubTopic}/{mqttSubTopic}",
            arguments = listOf(
                navArgument(EDGE_SERVER_ID_ARG) { type = NavType.IntType },
                navArgument(EDGE_DEVICE_ID_ARG) { type = NavType.IntType },
                navArgument(DEVICE_PROCESS_ID) { type = NavType.IntType },
                navArgument("mqttPubTopic") { type = NavType.StringType },
                navArgument("mqttSubTopic") { type = NavType.StringType }
            )
        ) {
            val arguments = it.arguments
            val edgeServerId = arguments?.getInt(EDGE_SERVER_ID_ARG) ?: return@composable
            val edgeDeviceId = arguments.getInt(EDGE_DEVICE_ID_ARG) ?: return@composable
            val processId = arguments.getInt(DEVICE_PROCESS_ID) ?: return@composable
            val mqttPubTopic = arguments.getString("mqttPubTopic") ?: return@composable
            val mqttSubTopic = arguments.getString("mqttSubTopic") ?: return@composable

            val viewModel = hiltViewModel<DetailEdgeDeviceViewModel>()

            DetailEdgeDeviceScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                navHostController = navController,
                edgeDeviceId = edgeDeviceId.toUInt(),
                edgeServerId = edgeServerId.toUInt(),
                processId = processId,
                mqttPubTopic = mqttPubTopic,
                mqttSubTopic = mqttSubTopic
            )
        }

        composable(
            route = Screen.UpdateEdgeDevice.screenRoute + "/{$EDGE_SERVER_ID_ARG}/{$EDGE_DEVICE_ID_ARG}/{mqttPubTopic}/{mqttSubTopic}",
            arguments = listOf(
                navArgument(EDGE_SERVER_ID_ARG) { type = NavType.IntType },
                navArgument(EDGE_DEVICE_ID_ARG) { type = NavType.IntType },
                navArgument("mqttPubTopic") { type = NavType.StringType },
                navArgument("mqttSubTopic") { type = NavType.StringType },

                )
        ) {
            val arguments = it.arguments
            val edgeServerId = arguments?.getInt(EDGE_SERVER_ID_ARG) ?: return@composable
            val edgeDeviceId = arguments.getInt(EDGE_DEVICE_ID_ARG) ?: return@composable
            val mqttPubTopic = arguments.getString("mqttPubTopic") ?: return@composable
            val mqttSubTopic = arguments.getString("mqttSubTopic") ?: return@composable

            val viewModel = hiltViewModel<UpdateEdgeDeviceViewModel>()

            UpdateEdgeDeviceScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                edgeServerId = edgeServerId.toUInt(),
                edgeDeviceId = edgeDeviceId.toUInt(),
                mqttSubTopic = mqttSubTopic,
                mqttPubTopic = mqttPubTopic,
                navHostController = navController
            )
        }

        composable(
            route = Screen.DetailEdgeDeviceSensor.screenRoute + "/{$EDGE_SERVER_ID_ARG}/{$EDGE_DEVICE_ID_ARG}/{$DEVICE_PROCESS_ID}/{mqttPubTopic}/{mqttSubTopic}",
            arguments = listOf(
                navArgument(EDGE_SERVER_ID_ARG) { type = NavType.IntType },
                navArgument(EDGE_DEVICE_ID_ARG) { type = NavType.IntType },
                navArgument(DEVICE_PROCESS_ID) { type = NavType.IntType },
                navArgument("mqttPubTopic") { type = NavType.StringType },
                navArgument("mqttSubTopic") { type = NavType.StringType },
            )
        ) {
            val arguments = it.arguments
            val edgeServerId = arguments?.getInt(EDGE_SERVER_ID_ARG) ?: return@composable
            val edgeDeviceId = arguments.getInt(EDGE_DEVICE_ID_ARG) ?: return@composable
            val processId = arguments.getInt(DEVICE_PROCESS_ID) ?: return@composable
            val mqttPubTopic = arguments.getString("mqttPubTopic") ?: return@composable
            val mqttSubTopic = arguments.getString("mqttSubTopic") ?: return@composable


            val viewModel = hiltViewModel<DetailEdgeDeviceSensorViewModel>()
            DetailEdgeDeviceSensorScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                edgeServerId = edgeServerId.toUInt(),
                edgeDeviceId = edgeDeviceId.toUInt(),
                processId = processId,
                mqttPubTopic = mqttPubTopic,
                mqttSubTopic = mqttSubTopic,
                navHostController = navController
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
    object DetailEdgeDeviceSensor : Screen(screenRoute = "detailEdgeDeviceSensor")

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