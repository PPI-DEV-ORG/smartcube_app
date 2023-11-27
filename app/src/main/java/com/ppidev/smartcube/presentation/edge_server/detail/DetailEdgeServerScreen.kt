package com.ppidev.smartcube.presentation.edge_server.detail

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.common.EDGE_SERVER_ID_ARG
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.CustomTab
import com.ppidev.smartcube.ui.theme.Purple40
import com.ppidev.smartcube.ui.theme.Typography
import com.ppidev.smartcube.utils.getNumberFromPercentage
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun DetailEdgeServerScreen(
    state: DetailEdgeServerState,
    onEvent: (event: DetailEdgeServerEvent) -> Unit,
    navHostController: NavHostController,
    edgeServerId: UInt,
    edgeServerAccessToken: String? = null
) {
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(Unit) {
        onEvent(DetailEdgeServerEvent.GetDetailDevicesInfo(edgeServerId))
    }

    LaunchedEffect(state.edgeDevicesInfo) {
        onEvent(DetailEdgeServerEvent.ListenMqtt)
    }

    DisposableEffect(Unit) {
        onDispose {
            onEvent(DetailEdgeServerEvent.UnListenMqtt)
        }
    }

    if (state.edgeDevicesInfo != null) {
        if (edgeServerAccessToken == null) {
            Text(text = "${state.edgeDevicesInfo.name}")
            LazyColumn {
                item {
                    CardServerInfo(
                        avgCpuTemp = state.serverInfo?.cpuTemp ?: "0",
                        fanSpeed = state.serverInfo?.fanSpeed ?: "0",
                        totalRam = state.serverInfo?.memoryTotal ?: "0",
                        upTime = state.serverInfo?.upTime ?: "0"
                    )
                }

                item {
                    CardStorageIndicator(
                        storageProgressIndicator = getNumberFromPercentage(
                            state.serverInfo?.storage?.diskUsage ?: "0%"
                        ).div(100),
                        totalStorage = state.serverInfo?.storage?.totalSpace ?: "0",
                        freeStorage = state.serverInfo?.storage?.freeSpace ?: "0"
                    )
                }

                item {
                    val pagerState = rememberPagerState(
                        pageCount = { 2 }
                    )
                    val coroutineScope = rememberCoroutineScope()

                    CustomTab(
                        currentPage = pagerState.currentPage,
                        onChangePage = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        },
                        listPage = listOf("MQTT Config", "Server Config")
                    )

                    HorizontalPager(state = pagerState, userScrollEnabled = false) { page ->

                        if (page == 0) {
                            Column {
                                CardTopic(
                                    title = "Topic (send)",
                                    token = "${state.edgeDevicesInfo.mqttPubTopic}"
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                CardTopic(
                                    title = "Topic (Receive)",
                                    token = "${state.edgeDevicesInfo.mqttSubTopic}"
                                )
                            }
                        } else if (page == 1) {
                            Column {
                                CardServerSummary(
                                    serverName = state.edgeDevicesInfo.name ?: "",
                                    vendor = state.edgeDevicesInfo.vendor ?: "",
                                    description = state.edgeDevicesInfo.description ?: ""
                                )
                                Spacer(modifier = Modifier.size(24.dp))

                                Text(text = "Devices Connected")
                                Spacer(modifier = Modifier.size(8.dp))

                                state.devices.map { item ->
                                    CardCamera(
                                        type = item.sourceType,
                                        name = item.type,
                                        status = false
                                    )
                                }
                            }

                        }
                    }
                }

                item {

                }

                item {

                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Please setup your edge server with this config :")
                Spacer(modifier = Modifier.size(12.dp))

                Text(text = "Mqtt (send) : ${state.edgeDevicesInfo.mqttPubTopic}")
                Text(text = "Mqtt (receive) : ${state.edgeDevicesInfo.mqttSubTopic}")
                Text(text = "server access token : $edgeServerAccessToken")

                IconButton(onClick = {
                    clipboardManager.setText(AnnotatedString(edgeServerAccessToken))
                }) {
                    Icon(
                        imageVector = Icons.Outlined.CopyAll,
                        contentDescription = "copy access token"
                    )
                }

                Spacer(modifier = Modifier.size(44.dp))

                Text(text = "Done ?")

                Button(onClick = {
                    navHostController.navigate(Screen.DetailEdgeServer.screenRoute + "?$EDGE_SERVER_ID_ARG=${edgeServerId}")
                }) {
                    Text(text = "Next")
                }
            }
        }
    }
}

@Composable
fun CardCamera(type: String, name: String, status: Boolean) {
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
            Icon(
                imageVector = Icons.Outlined.Videocam,
                contentDescription = "description",
                modifier = Modifier.size(44.dp)
            )
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
fun CardServerSummary(
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
            imageVector = Icons.Filled.DeviceHub,
            contentDescription = null,
            modifier = Modifier.size(74.dp)
        )
    }
}

@Composable
fun CardTopic(modifier: Modifier = Modifier, title: String, token: String) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color(0xFFBCBCBC),
                shape = RoundedCornerShape(size = 4.dp)
            )
            .fillMaxWidth()
            .height(44.dp)
            .padding(12.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 4.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Text(text = token)
    }
}

@Composable
fun CardStorageIndicator(
    modifier: Modifier = Modifier,
    storageProgressIndicator: Float,
    totalStorage: String,
    freeStorage: String
) {
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
        Text(text = "40%")
    }
}

@Composable
fun CardServerInfo(
    modifier: Modifier = Modifier,
    avgCpuTemp: String,
    totalRam: String,
    fanSpeed: String,
    upTime: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(Color(0xFFF8F8F8))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(

            ) {
                Text(
                    text = "Server Info",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(text = "Processor temp (avg): $avgCpuTemp", fontSize = 12.sp)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Total Ram Space: $totalRam", fontSize = 12.sp)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Fan Speed: $fanSpeed", fontSize = 12.sp)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Up time: $upTime", fontSize = 12.sp)
            }

            AnimatedCircularProgressIndicator(
                currentValue = 40,
                maxValue = 100,
                progressBackgroundColor = Color.LightGray,
                progressIndicatorColor = Purple40,
                midColor = Color.Red,
                midValue = 50
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardServerInfoPreview() {
}


@Composable
fun AnimatedCircularProgressIndicator(
    currentValue: Int,
    midValue: Int,
    maxValue: Int,
    progressBackgroundColor: Color,
    progressIndicatorColor: Color,
    midColor: Color,
    modifier: Modifier = Modifier
) {

    val stroke = with(LocalDensity.current) {
        Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        ProgressStatus(
            currentValue = currentValue,
            progressBackgroundColor = progressBackgroundColor,
            progressIndicatorColor = progressIndicatorColor,
            midColor = midColor,
            midValue = midValue
        )

        val animateFloat = remember { Animatable(0f) }
        LaunchedEffect(animateFloat) {
            animateFloat.animateTo(
                targetValue = currentValue / maxValue.toFloat(),
                animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
            )
        }

        Canvas(
            Modifier
                .progressSemantics(currentValue / maxValue.toFloat())
                .size(CircularIndicatorDiameter)
        ) {
            // Start at 12 O'clock
            val startAngle = 90f
            val sweep: Float = animateFloat.value * 360f
            val diameterOffset = stroke.width / 2

            drawCircle(
                color = progressBackgroundColor,
                style = stroke,
                radius = size.minDimension / 2.0f - diameterOffset
            )

            drawCircularProgressIndicator(
                startAngle,
                sweep,
                if (currentValue <= midValue) progressIndicatorColor else midColor,
                stroke
            )

//            if (currentValue >= midValue) {
//                drawCircle(
//                    color = midColor,
//                    style = stroke,
//                    radius = size.minDimension / 2.0f - diameterOffset
//                )
//            }
        }
    }
}

@Composable
private fun ProgressStatus(
    currentValue: Int,
    midValue: Int,
    progressBackgroundColor: Color,
    progressIndicatorColor: Color,
    midColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            val emphasisSpan =
                Typography.titleLarge.copy(
                    color = if (currentValue <= midValue) {
                        progressIndicatorColor
                    } else {
                        midColor
                    },
                    fontWeight = FontWeight.Bold
                ).toSpanStyle()
            val defaultSpan =
                Typography.bodyMedium.copy(
                    color = progressBackgroundColor,
                    fontWeight = FontWeight.Medium
                ).toSpanStyle()
            append(AnnotatedString("$currentValue", spanStyle = emphasisSpan))
            append(AnnotatedString(text = "%", spanStyle = defaultSpan))
        },
    )
}

private fun DrawScope.drawCircularProgressIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

private val CircularIndicatorDiameter = 84.dp