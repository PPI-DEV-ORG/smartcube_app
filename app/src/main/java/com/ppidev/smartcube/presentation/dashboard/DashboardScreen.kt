package com.ppidev.smartcube.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ppidev.smartcube.ui.components.HeaderSection
import com.ppidev.smartcube.ui.theme.SmartcubeAppTheme
import kotlinx.coroutines.delay

@Composable
fun DashboardScreen(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit
) {
    val intervalSync = 10000L // 10 second

//    val context = LocalContext.current

//    val notificationManager = MyNotificationManager(context)

//    fun showNotification() {
//        notificationManager.showNotification(channel = ENotificationChannel.TEST_CHANNEL) {
//            setTitle("TEST TITLE")
//            setBody("test desc")
//
//            setSmallIcon(R.drawable.ic_stat_notification)
//
//            val intent = Intent(
//                Intent.ACTION_VIEW,
//                "$NOTIFICATION_URL/$NOTIFICATION_ARG=${123}".toUri(),
//                context,
//                MainActivity::class.java
//            )
//
//            val flag =
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                    PendingIntent.FLAG_IMMUTABLE
//                else
//                    0
//
//            setIntent(
//                intent
//            )
//            setPendingIntent(
//                TaskStackBuilder.create(context).run {
//                    addNextIntentWithParentStack(intent)
//                    getPendingIntent(1, flag)
//                }
//            )
//        }
//    }

    LaunchedEffect(Unit) {
        onEvent(DashboardEvent.SubscribeToMqttService)
        onEvent(DashboardEvent.GetListModelInstalled)
        onEvent(DashboardEvent.GetDeviceConfig)
    }

    DisposableEffect(Unit) {
        onDispose {
            onEvent(DashboardEvent.UnsubscribeToMqttService)
        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(bottom = 80.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        item {
            UserSection(modifier = Modifier.padding(bottom = 18.dp))
        }

        item {
            HeaderSection(title = "Latest notifications", isShowMoreExist = false)
        }

        item {
            LatestNotificationSection(
                isLoading = state.isLoadingNotification,
                listNotification = state.notifications,
                modifier = Modifier
                    .padding(bottom = 24.dp)
            )
        }

        item {
            HeaderSection(title = "Server summary", isShowMoreExist = false)
        }

        item {
            ServerSummarySection(
                resourceState = state.serverSummary,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        item {
            HeaderSection(title = "List models", isShowMoreExist = true, onClickShowMore = {})
        }

        itemsIndexed(state.listModelsML) { _, d ->
            CardModelItem(modelName = d.name, version = d.version)
        }

        item {
            HeaderSection(title = "List cameras", isShowMoreExist = true, onClickShowMore = {})
        }

        itemsIndexed(state.listDevicesConfig) { _, d ->
            CardCameraItem(type = d.type, deviceLocation = d.additionalInfo.deviceLocation)
        }
    }

    LaunchedEffect(state.serverSummary) {
        while (true) {

            delay(intervalSync)

            onEvent(DashboardEvent.GetServerSummary)

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    SmartcubeAppTheme {
        DashboardScreen(
            state = DashboardState(
                notifications = emptyList(),
            ),
            onEvent = {
//                when (it) {
//                    DashboardEvent.GetServerSummary -> Log.d(
//                        "DASHBOARD_EVENT",
//                        "get server summary"
//                    )
//
//                    DashboardEvent.SubscribeToMqttService -> Log.d(
//                        "DASHBOARD_EVENT",
//                        "subscribe to topic"
//                    )
//
//                    DashboardEvent.UnsubscribeToMqttService -> Log.d(
//                        "DASHBOARD_EVENT",
//                        "unsubscribe to topic"
//                    )
//                }
            }
        )
    }
}