package com.ppidev.smartcube.presentation.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
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
import com.ppidev.smartcube.domain.model.TypeEdgeDevice
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.utils.extractFloatFromString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val memoryUsage: Float = try {
            val memoryFree = state.serverInfoMQTT?.memoryFree?.takeIf { it.isNotEmpty() }
                ?.let { extractFloatFromString(it) } ?: 0f
            val totalMemory = state.serverInfoMQTT?.memoryTotal?.takeIf { it.isNotEmpty() }
                ?.let { extractFloatFromString(it) } ?: 0f

            val result = if (totalMemory != 0.0f) memoryFree / totalMemory else 0f
            ((result * 100) * 10).roundToInt().toFloat() / 10
        } catch (e: NumberFormatException) {
            0f
        }
        DashboardContentView(
            username = state.username,
            email = state.email,
            avgCpuTemp = state.serverInfoMQTT?.cpuTemp ?: "-",
            upTime = state.serverInfoMQTT?.upTime ?: "-",
            totalRam = state.serverInfoMQTT?.memoryFree ?: "-",
            fanSpeed = state.serverInfoMQTT?.fanSpeed ?: "-",
            selectedTabIndex = selectedTabIndex,
            listEdgeServer = state.listServer,
            listEdgeDevices = state.listDevices,
            isLoadingProfile = false,
            isLoadingListDevices = state.loading.isLoadingListDevices,
            isLoadingListServer = state.loading.isLoadingListServer,
            ramUsage = memoryUsage,
            ramFree = state.serverInfoMQTT?.memoryFree ?: "-",
            errors = state.error,
            onTabChange = { index, _ ->
                selectedTabIndex = index
                onEvent(DashboardEvent.UnsubscribeToMqttService)
                onEvent(DashboardEvent.SetToEmptyServerInfo)
                onEvent(DashboardEvent.SetEdgeServerId(state.listServerId[index]))
            },
            navigateToDetailDevice = { index, device ->
                if (device.type == TypeEdgeDevice.CAMERA.typeName) {
                    navHostController.navigate(
                        Screen.DetailEdgeDevice.withArgs(
                            state.edgeServerId.toString(),
                            device.id.toString(),
                            index.toString(),
                            device.vendorName,
                            device.type
                        )
                    )
                } else if (device.type == TypeEdgeDevice.SENSOR.typeName) {
                    navHostController.navigate(
                        Screen.DetailEdgeDeviceSensor.withArgs(
                            state.edgeServerId.toString(),
                            device.id.toString(),
                        )
                    )
                }
            },
            navigateCreateNewServer = {
                navHostController.navigate(Screen.FormAddEdgeServer.screenRoute)
            },
            navigateCreateNewDevice = {
                val serverId = state.edgeServerId
                if (serverId != null) {
                    navHostController.navigate(Screen.FormAddEdgeDevice.withArgs("${state.edgeServerId}"))
                }
            },
        )
    }

    LaunchedEffect(state.mqttPublishTopic) {
        if (state.mqttPublishTopic != null) {
            while (true) {
                delay(intervalSync)
                onEvent(DashboardEvent.GetServerInfoMqtt(state.mqttPublishTopic))
            }
        }
    }
}