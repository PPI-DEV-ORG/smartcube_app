package com.ppidev.smartcube.presentation.edge_device.detail.sensor

import com.ppidev.smartcube.data.remote.dto.DetailEdgeDeviceDto
import com.ppidev.smartcube.domain.model.NotificationModel
import com.ppidev.smartcube.utils.FilterChart
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DetailEdgeDeviceSensorState(
    val isLoadingSensorData: Boolean = false,
    val isLoadingDeviceDetail: Boolean = false,
    val isLoadingNotificationDetail: Boolean = false,
    val listTemperatureData: List<Pair<Long, Float>> = emptyList(),
    val listHumidityData: List<Pair<Long, Float>> = emptyList(),
    val listSmokeData: List<Pair<Long, Float>> = emptyList(),

    val temperatureUnitMeasurement: String = "°C",
    val humidityUnitMeasurement: String = "%",
    val gasUnitMeasurement: String = "kΩ",

    val filterChart: FilterChart = FilterChart.OneDay,

    val notificationId: UInt? = null,
    val detailNotification: NotificationModel? = null,
    val edgeDeviceDetail: DetailEdgeDeviceDto? = null,
    val startDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
    val endDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
)