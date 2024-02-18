package com.ppidev.smartcube.presentation.edge_device.detail.sensor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import com.ppidev.smartcube.R
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.ui.components.CustomTab
import com.ppidev.smartcube.ui.components.card.CardDetailNotification
import com.ppidev.smartcube.ui.components.card.CardNotification
import com.ppidev.smartcube.ui.components.rememberMarker
import com.ppidev.smartcube.ui.components.shimmerEffect
import com.ppidev.smartcube.utils.FilterChart
import com.ppidev.smartcube.utils.createAxisValueFormatter
import com.ppidev.smartcube.utils.isoDateFormatToStringDate
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailEdgeDeviceSensorScreen(
    state: DetailEdgeDeviceSensorState,
    onEvent: (event: DetailEdgeDeviceSensorEvent) -> Unit,
    edgeServerId: UInt,
    edgeDeviceId: UInt,
    processId: Int,
    mqttSubTopic: String,
    mqttPubTopic: String,
    navHostController: NavHostController
) {
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )
    val coroutineScope = rememberCoroutineScope()
    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }
    val listItems: ArrayList<MenuItemData> = arrayListOf(
        MenuItemData(text = "Edit", icon = Icons.Filled.Edit),
        MenuItemData(text = "Delete", icon = Icons.Filled.Delete)
    )

    LaunchedEffect(Unit) {
        onEvent(DetailEdgeDeviceSensorEvent.GetDetailDevice(edgeServerId, edgeDeviceId))
        onEvent(
            DetailEdgeDeviceSensorEvent.GetSensorData(
                edgeDeviceId,
                edgeServerId,
                state.startDate,
                state.endDate
            )
        )
    }

    LaunchedEffect(key1 = state.filterChart) {
        onEvent(
            DetailEdgeDeviceSensorEvent.GetSensorData(
                edgeDeviceId,
                edgeServerId,
                state.startDate,
                state.endDate
            )
        )
    }

    val detailEdgeDevice = state.edgeDeviceDetail


    Column {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                    Text(text = "Sensor Device")
                }
            },
            actions = {
                IconButton(onClick = {
                    expanded = true
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Open Options"
                    )
                }

                // drop down menu
                DropdownMenu(
                    modifier = Modifier.width(width = 150.dp),
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                    offset = DpOffset(x = (-102).dp, y = (-10).dp),
                    properties = PopupProperties()
                ) {
                    listItems.forEach { menuItemData ->
                        DropdownMenuItem(
                            onClick = {
                                when (menuItemData.text) {
                                    "Edit" -> {
                                        navHostController.navigate(
                                            Screen.UpdateEdgeDevice.withArgs(
                                                "$edgeServerId",
                                                "$edgeDeviceId",
                                                mqttPubTopic,
                                                mqttSubTopic
                                            )
                                        )
                                    }

                                    "Delete" -> {

                                    }
                                }
                                expanded = false
                            },
                            enabled = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = menuItemData.icon,
                                    contentDescription = menuItemData.text,
                                    tint = Color.DarkGray
                                )
                            },
                            text = {
                                Text(
                                    text = menuItemData.text,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = Color.DarkGray
                                )
                            }
                        )
                    }
                }
            })

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                ) {
                    Text(
                        modifier = Modifier.width(165.dp),
                        text = detailEdgeDevice?.vendorName ?: "-",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.size(18.dp))
                    Text(
                        text = "Source : ${detailEdgeDevice?.sourceAddress ?: "-"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Icon(
                    modifier = Modifier.size(46.dp),
                    painter = painterResource(id = R.drawable.ic_sensor),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.size(56.dp))

            CustomTab(
                currentPage = pagerState.currentPage,
                onChangePage = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
                listPage = listOf("Notification", "Status")
            )

            HorizontalPager(
                verticalAlignment = Alignment.Top,
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                if (page == 0) {
                    if (detailEdgeDevice != null) {
                        Column {
                            AnimatedVisibility(
                                visible = state.notificationId != null && state.detailNotification != null,
                            ) {
                                CardDetailNotification(
                                    title = state.detailNotification?.title.orEmpty(),
                                    date = isoDateFormatToStringDate(
                                        state.detailNotification?.createdAt ?: ""
                                    )
                                        ?: "-",
                                    serverName = "${state.detailNotification?.edgeServerId}",
                                    deviceName = "${state.detailNotification?.edgeDeviceId}",
                                    description = state.detailNotification?.description.orEmpty(),
                                    type = state.detailNotification?.deviceType.orEmpty(),
                                    imgUrl = state.detailNotification?.imageUrl,
                                    riskLevel = state.detailNotification?.riskLevel,
                                    objectLabel = state.detailNotification?.objectLabel
                                )
                            }

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                itemsIndexed(
                                    items = detailEdgeDevice.notifications,
                                    key = { _, d -> d.id }) { _, item ->
                                    CardNotification(
                                        title = item.title,
                                        date = isoDateFormatToStringDate(item.createdAt) ?: "-",
                                        type = item.deviceType,
                                        imgUrl = item.image,
                                        server = detailEdgeDevice.edgeServers[0].name,
                                        device = detailEdgeDevice.vendorName,
                                        isFocus = state.notificationId == item.id.toUInt(),
                                        onClick = {
                                            onEvent(
                                                DetailEdgeDeviceSensorEvent.SetNotificationId(
                                                    item.id.toUInt()
                                                )
                                            )
                                            onEvent(
                                                DetailEdgeDeviceSensorEvent.GetNotificationDetail(
                                                    edgeServerId,
                                                    item.id.toUInt()
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                } else if (page == 1) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                shape = RoundedCornerShape(8.dp),
                                onClick = {
                                    onEvent(DetailEdgeDeviceSensorEvent.SetFilterChart(FilterChart.OneWeek))
                                },
                                colors = if (state.filterChart == FilterChart.OneWeek) {
                                    ButtonDefaults.buttonColors()
                                } else {
                                    ButtonDefaults.buttonColors(
                                        containerColor = Color.LightGray,
                                        contentColor = Color.Black
                                    )
                                },
                            ) {
                                Text(
                                    text = "1 Week",
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                            Button(
                                shape = RoundedCornerShape(8.dp),
                                onClick = {
                                    onEvent(DetailEdgeDeviceSensorEvent.SetFilterChart(FilterChart.OneDay))
                                },
                                colors = if (state.filterChart == FilterChart.OneDay) {
                                    ButtonDefaults.buttonColors()
                                } else {
                                    ButtonDefaults.buttonColors(
                                        containerColor = Color.LightGray,
                                        contentColor = Color.Black
                                    )
                                }
                            ) {
                                Text(
                                    text = "Today",
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }

                        if (state.isLoadingSensorData) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .clip(
                                        RoundedCornerShape(10.dp)
                                    )
                                    .shimmerEffect()
                            ) {}
                        } else if (state.listTemperatureData.isNotEmpty()) {
                            val chartTemperatureEntryModel =
                                entryModelOf(state.listTemperatureData.mapIndexed { index, item ->
                                    entryOf(
                                        index,
                                        item.second
                                    )
                                })

                            val charHumidityEntryModel =
                                entryModelOf(state.listTemperatureData.mapIndexed { index, item ->
                                    entryOf(
                                        index,
                                        item.second
                                    )
                                })

                            val charSmokeEntryModel =
                                entryModelOf(state.listSmokeData.mapIndexed { index, item ->
                                    entryOf(
                                        index,
                                        item.second
                                    )
                                })

                            val horizontalAxisValueTemperature =
                                createAxisValueFormatter(
                                    state.listTemperatureData,
                                    state.filterChart
                                )

                            val horizontalAxisValueHumidity =
                                createAxisValueFormatter(state.listHumidityData, state.filterChart)

                            val horizontalAxisValueSmoke =
                                createAxisValueFormatter(state.listSmokeData, state.filterChart)

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                ProvideChartStyle {
                                    val temperatureMarker = rememberMarker()
                                    val humidityMarker = rememberMarker()
                                    val smokeMarker = rememberMarker()

                                    Chart(
                                        modifier = Modifier.height(300.dp),
                                        chart = lineChart(),
                                        model = chartTemperatureEntryModel,
                                        isZoomEnabled = true,
                                        chartScrollState = rememberChartScrollState(),
                                        startAxis = rememberStartAxis(
                                            title = "Temperature (Celsius)",
                                            valueFormatter = { value, _ ->
                                                "${value.toInt()} ${state.temperatureUnitMeasurement}"
                                            },
                                            tickLength = 0.dp,
                                            guideline = null
                                        ),
                                        bottomAxis = rememberBottomAxis(
                                            valueFormatter = horizontalAxisValueTemperature,
                                            tickLength = 0.dp,
                                            guideline = null,
                                            labelRotationDegrees = 90f
                                        ),
                                        marker = temperatureMarker,
                                    )

                                    Chart(
                                        modifier = Modifier.height(300.dp),
                                        chart = lineChart(),
                                        model = charHumidityEntryModel,
                                        isZoomEnabled = true,
                                        chartScrollState = rememberChartScrollState(),
                                        startAxis = rememberStartAxis(
                                            title = "Humidity",
                                            valueFormatter = { value, _ ->
                                                "${value.toInt()} ${state.humidityUnitMeasurement}"
                                            },
                                            tickLength = 0.dp,
                                            guideline = null
                                        ),
                                        bottomAxis = rememberBottomAxis(
                                            valueFormatter = horizontalAxisValueHumidity,
                                            tickLength = 0.dp,
                                            guideline = null,
                                            labelRotationDegrees = 90f
                                        ),
                                        marker = humidityMarker,
                                    )

                                    Chart(
                                        modifier = Modifier.height(300.dp),
                                        chart = lineChart(),
                                        model = charSmokeEntryModel,
                                        isZoomEnabled = true,
                                        chartScrollState = rememberChartScrollState(),
                                        startAxis = rememberStartAxis(
                                            title = "Temperature",
                                            valueFormatter = { value, _ ->
                                                "${value.toInt()}  ${state.gasUnitMeasurement}"
                                            },
                                            tickLength = 0.dp,
                                            guideline = null
                                        ),
                                        bottomAxis = rememberBottomAxis(
                                            valueFormatter = horizontalAxisValueSmoke,
                                            tickLength = 0.dp,
                                            guideline = null,
                                            labelRotationDegrees = 90f
                                        ),
                                        marker = smokeMarker,
                                    )
                                }
                            }
                        } else {
                            Text(text = "No Data to show")
                        }
                    }
                }
            }
        }
    }
}

data class MenuItemData(val text: String, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
fun DetailEdgeDeviceSensorScreenPreview() {
    DetailEdgeDeviceSensorScreen(
        state = DetailEdgeDeviceSensorState(),
        onEvent = {},
        edgeServerId = 0.toUInt(),
        edgeDeviceId = 0.toUInt(),
        processId = 0,
        mqttSubTopic = "",
        mqttPubTopic = "",
        navHostController = rememberNavController()
    )
}