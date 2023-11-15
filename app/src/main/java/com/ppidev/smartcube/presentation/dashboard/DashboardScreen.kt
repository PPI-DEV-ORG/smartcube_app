package com.ppidev.smartcube.presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ppidev.smartcube.ui.components.HeaderSection
import com.ppidev.smartcube.ui.components.WeatherCardV2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit
) {
    val intervalSync = 10000L
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

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
        onEvent(DashboardEvent.GetListNotification)
        onEvent(DashboardEvent.GetCurrentWeather)
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

//        item {
//            WeatherCardV2(
//                tempC = "${state.weather?.current?.tempC}° C",
//                feelsLike = "${state.weather?.current?.feelslikeC}° C",
//                location = "${state.weather?.location?.name}",
//                condition = "${state.weather?.current?.condition?.text}"
//            )
//        }

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
            HeaderSection(
                title = "Server summary",
                isShowMoreExist = true,
                textShowMore = "Change Server",
                onClickShowMore = {
                    onEvent(DashboardEvent.OpenBottomSheet)
                })
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

    if (state.openBottomSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BottomSheetDefaults.DragHandle()
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    ) {
                        Text(
                            text = "Select Edge Server",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Divider()
                }
            },
            onDismissRequest = { onEvent(DashboardEvent.CloseBottomSheet) }) {
            BottomSheetContent(
                indexSelected = state.serverSelected,
                onSelectServer = {
                    onEvent(DashboardEvent.OnSelectServer(it))
                },
                onHideButtonClick = {
                    scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            onEvent(DashboardEvent.CloseBottomSheet)
                        }
                    }
                }
            )
        }
    }

    LaunchedEffect(state.serverSummary) {
        while (true) {

            delay(intervalSync)

            onEvent(DashboardEvent.GetServerSummary)

        }
    }
}


@Composable
fun BottomSheetContent(
    onSelectServer: (index: Int) -> Unit,
    onHideButtonClick: () -> Unit,
    indexSelected: Int = 0,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        items(5) {
            ListItem(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onSelectServer(it)
                    },
                colors = ListItemDefaults.colors(
                    containerColor = if (indexSelected == it) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                    headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    leadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    overlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    supportingColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    trailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                headlineContent = { Text(text = "Server $it") },
                trailingContent = {
                    Icon(
                        imageVector = Icons.Filled.Circle,
                        contentDescription = null,
                        tint = if (indexSelected == it) Color.Green else Color.Red
                    )
                }
            )
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = onHideButtonClick
            ) {
                Text(text = "Close")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomSheetContent() {
    BottomSheetContent(
        onSelectServer = {},
        onHideButtonClick = {}
    )
}

//@Preview(showBackground = true)
//@Composable
//fun DashboardScreenPreview() {
//    SmartcubeAppTheme {
//        DashboardScreen(
//            state = DashboardState(
//                notifications = emptyList(),
//            ),
//            onEvent = {
////                when (it) {
////                    DashboardEvent.GetServerSummary -> Log.d(
////                        "DASHBOARD_EVENT",
////                        "get server summary"
////                    )
////
////                    DashboardEvent.SubscribeToMqttService -> Log.d(
////                        "DASHBOARD_EVENT",
////                        "subscribe to topic"
////                    )
////
////                    DashboardEvent.UnsubscribeToMqttService -> Log.d(
////                        "DASHBOARD_EVENT",
////                        "unsubscribe to topic"
////                    )
////                }
//            }
//        )
//    }
//}