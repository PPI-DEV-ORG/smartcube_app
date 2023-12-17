package com.ppidev.smartcube.presentation.edge_server.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.TypeEdgeDevice
import com.ppidev.smartcube.ui.components.CustomTab
import com.ppidev.smartcube.ui.components.card.CardServerInfo
import com.ppidev.smartcube.ui.components.modal.ModalInfoServerConfig
import com.ppidev.smartcube.ui.theme.Typography
import com.ppidev.smartcube.utils.extractFloatFromString
import com.ppidev.smartcube.utils.getNumberFromPercentage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun DetailEdgeServerScreen(
    state: DetailEdgeServerState,
    onEvent: (event: DetailEdgeServerEvent) -> Unit,
    navHostController: NavHostController,
    edgeServerId: UInt,
    edgeServerAccessToken: String? = null
) {
    LaunchedEffect(Unit) {
        onEvent(DetailEdgeServerEvent.GetDetailDevicesInfo(edgeServerId))
        if (edgeServerAccessToken != null) {
            onEvent(DetailEdgeServerEvent.SetDialogStatus(true))
        }
    }

    LaunchedEffect(state.edgeDevicesInfo) {
        if (state.edgeDevicesInfo != null) {
            onEvent(DetailEdgeServerEvent.SubscribeToTopicMqtt(state.edgeDevicesInfo.mqttSubTopic))
        }
    }

    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )
    val coroutineScope = rememberCoroutineScope()

    Column {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back")
                }
                Text(text = "${state.edgeDevicesInfo?.name}")
            }
        })

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            val memoryUsage: Float = try {
                val memoryFree = state.serverInfo?.memoryFree?.takeIf { it.isNotEmpty() }
                    ?.let { extractFloatFromString(it) } ?: 0f
                val totalMemory = state.serverInfo?.memoryTotal?.takeIf { it.isNotEmpty() }
                    ?.let { extractFloatFromString(it) } ?: 0f

                val result = if (totalMemory != 0.0f) memoryFree / totalMemory else 0f
                ((result * 100) * 10).roundToInt().toFloat() / 10
            } catch (e: NumberFormatException) {
                0f
            }

            CardServerInfo(
                avgCpuTemp = state.serverInfo?.cpuTemp ?: "-",
                fanSpeed = state.serverInfo?.fanSpeed ?: "-",
                totalRam = state.serverInfo?.memoryTotal ?: "-",
                upTime = state.serverInfo?.upTime ?: "-",
                ramFree = state.serverInfo?.memoryFree ?: "-",
                ramUsage = memoryUsage
            )

            Spacer(modifier = Modifier.size(14.dp))

            CardStorageIndicator(
                storageProgressIndicator = getNumberFromPercentage(
                    state.serverInfo?.storage?.diskUsage ?: "0%"
                ).div(100),
                totalStorage = state.serverInfo?.storage?.totalSpace ?: "0",
                freeStorage = state.serverInfo?.storage?.freeSpace ?: "0"
            )

            Spacer(modifier = Modifier.size(22.dp))

            CustomTab(
                currentPage = pagerState.currentPage,
                onChangePage = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
                listPage = listOf("MQTT Config", "Server Config")
            )

            Spacer(modifier = Modifier.size(16.dp))

            HorizontalPager(
                verticalAlignment = Alignment.Top,
                state = pagerState,
                userScrollEnabled = false
            ) { page ->

                if (page == 0) {
                    Column {
                        CardTopic(
                            title = "Topic (send)",
                            token = "${state.edgeDevicesInfo?.mqttPubTopic}"
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        CardTopic(
                            title = "Topic (Receive)",
                            token = "${state.edgeDevicesInfo?.mqttSubTopic}"
                        )
                    }
                } else if (page == 1) {
                    Column {
                        CardServerSummary(
                            serverName = state.edgeDevicesInfo?.name ?: "",
                            vendor = state.edgeDevicesInfo?.vendor ?: "",
                            description = state.edgeDevicesInfo?.description ?: ""
                        )
                        Spacer(modifier = Modifier.size(24.dp))
                        Text(
                            text = "Devices Connected",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        state.devices.map { item ->
                            key(item.id) {
                                CardDevice(
                                    type = item.sourceType,
                                    name = item.type,
                                    status = true
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                            }
                        }
                    }

                }
            }
        }
    }

    if (state.isDialogOpen) {
        ModalInfoServerConfig(
            publishTopic = state.edgeDevicesInfo?.mqttPubTopic ?: "-",
            subscribeTopic = state.edgeDevicesInfo?.mqttSubTopic ?: "-",
            edgeServerToken = edgeServerAccessToken ?: "-",
            onClose = {
                onEvent(DetailEdgeServerEvent.SetDialogStatus(false))
            }
        )
    }

    LaunchedEffect(state.edgeDevicesInfo) {
        while (true) {
            delay(1000L)
            if (state.edgeDevicesInfo != null) {
                onEvent(DetailEdgeServerEvent.GetServerInfoMqtt(state.edgeDevicesInfo.mqttPubTopic))
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (state.edgeDevicesInfo != null) {
                onEvent(DetailEdgeServerEvent.UnSubscribeFromMqttTopic(state.edgeDevicesInfo.mqttSubTopic))
            }
        }
    }
}

@Composable
private fun CardDevice(type: String, name: String, status: Boolean) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFFD1D1D1),
                shape = RoundedCornerShape(size = 4.dp)
            )
            .fillMaxSize()
            .height(60.dp)
            .clip(
                RoundedCornerShape(4.dp)
            )
            .background(Color(0xFFF8F8F8))
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (type == TypeEdgeDevice.CAMERA.typeName) {
                Icon(
                    painterResource(id = R.drawable.ic_camera),
                    contentDescription = null,
                    modifier = Modifier.size(44.dp)
                )
            } else {
                Icon(
                    painterResource(id = R.drawable.ic_sensor),
                    contentDescription = null,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.size(8.dp))
            Text(text = name, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }

        Icon(
            imageVector = Icons.Filled.Circle,
            contentDescription = null,
            tint = if (!status) Color.Red else Color.Green
        )
    }

}

@Composable
private fun CardServerSummary(
    serverName: String,
    vendor: String,
    description: String
) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFFD1D1D1),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .fillMaxWidth()
            .height(138.dp)
            .background(
                color = Color(0xFFFBFBFB), shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.width(232.dp)
        ) {
            Text(
                text = serverName, style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000)
                )
            )
            Text(
                text = "Vendor : $vendor", style = TextStyle(
                    fontSize = 10.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF444444),
                )
            )

            Text(
                text = description,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_cube),
            contentDescription = null,
            modifier = Modifier.size(74.dp)
        )
    }
}

@Composable
private fun CardTopic(modifier: Modifier = Modifier, title: String, token: String) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color(0xFFBCBCBC),
                shape = RoundedCornerShape(size = 4.dp)
            )
            .fillMaxWidth()
            .height(64.dp)
            .padding(12.dp)
            .clip(RoundedCornerShape(size = 4.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Text(text = token)
    }
}

@Composable
private fun CardStorageIndicator(
    modifier: Modifier = Modifier,
    storageProgressIndicator: Float,
    totalStorage: String,
    freeStorage: String
) {
    val toPercentage = (storageProgressIndicator * 100).roundToInt()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x40C6C6C6),
                ambientColor = Color(0x40C6C6C6)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Outlined.Storage,
            contentDescription = null,
            modifier = Modifier.padding(6.dp)
        )
        Column {
            Text(text = "Storage")
            Spacer(modifier = Modifier.size(3.dp))
            LinearProgressIndicator(storageProgressIndicator)

            Text(text = buildAnnotatedString {
                val defaultSpan =
                    Typography.bodyMedium.copy(
                    ).toSpanStyle()
                append(AnnotatedString("Total: $totalStorage", spanStyle = defaultSpan))
                append(AnnotatedString(text = ", ", spanStyle = defaultSpan))
                append(AnnotatedString(text = "Free: $freeStorage", spanStyle = defaultSpan))
            })
        }
        Text(text = "$toPercentage%")
    }
}

