package com.ppidev.smartcube.presentation.dashboard

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.ppidev.smartcube.ui.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun DashboardScreen(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
    navHostController: NavHostController
) {
    val intervalSync = 10000L
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = -available.y
                coroutineScope.launch {
                    if (scrollState.isScrollInProgress.not()) {
                        scrollState.scrollBy(delta)
                    }
                }
                return Offset.Zero
            }
        }
    }

    LaunchedEffect(Unit) {
        onEvent(DashboardEvent.GetListEdgeServer)
        onEvent(DashboardEvent.GetCurrentWeather)
    }

    LaunchedEffect(key1 = state.edgeServerId) {
        if (state.edgeServerId != null) {
            onEvent(DashboardEvent.GetDevicesConfig(state.edgeServerId))
        }
    }

    LaunchedEffect(key1 = state.mqttSubscribeTopic) {
        if (state.mqttSubscribeTopic != null) {
            onEvent(DashboardEvent.SubscribeTopicMqtt(state.mqttSubscribeTopic))
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            onEvent(DashboardEvent.UnsubscribeToMqttService)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        DashboardContentView(
            username = state.username,
            email = state.email,
            selectedTabIndex = selectedTabIndex,
            listEdgeServer = state.listServer,
            listEdgeDevices = state.listDevices,
            isLoadingProfile = false,
            isLoadingListDevices = state.loading.isLoadingListDevices,
            isLoadingListServer = state.loading.isLoadingListServer,
            onTabChange = { index, _ ->
                selectedTabIndex = index
                onEvent(DashboardEvent.UnsubscribeToMqttService)
                onEvent(DashboardEvent.SetEdgeServerId(state.listServerId[index]))
            },
            navigateToDetailDevice = {
            },
            navigateCreateNewServer = {
                navHostController.navigate(Screen.FormAddEdgeServer.screenRoute)
            },
            navigateCreateNewDevice = {
                val serverId = state.edgeServerId
                if (serverId != null) {
                    navHostController.navigate(Screen.FormAddEdgeDevice.withArgs("${state.edgeServerId}"))
                }
            }
        )
    }

    LaunchedEffect(state.mqttPublishTopic) {
        while (true) {
            delay(intervalSync)

            if (state.mqttPublishTopic != null) {
                onEvent(DashboardEvent.GetServerInfoMqtt(state.mqttPublishTopic))
            }
        }
    }
}