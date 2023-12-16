package com.ppidev.smartcube.presentation.edge_device.detail.sensor

import com.ppidev.smartcube.utils.FilterChart

sealed class DetailEdgeDeviceSensorEvent {
    data class GetDataNotificationsByDevice(val serverId: UInt, val deviceId: UInt) :
        DetailEdgeDeviceSensorEvent()

    data class GetDetailDevice(val serverId: UInt, val deviceId: UInt) :
        DetailEdgeDeviceSensorEvent()

    data class GetSensorData(
        val deviceId: UInt,
        val serverId: UInt,
        val startDate: String,
        val endDate: String
    ) : DetailEdgeDeviceSensorEvent()

    data class SetNotificationId(
        val notificationId: UInt
    ) : DetailEdgeDeviceSensorEvent()

    data class GetNotificationDetail(val edgeServerId: UInt, val notificationId: UInt) :
        DetailEdgeDeviceSensorEvent()
    data class SetFilterChart(val filter: FilterChart) : DetailEdgeDeviceSensorEvent()
}