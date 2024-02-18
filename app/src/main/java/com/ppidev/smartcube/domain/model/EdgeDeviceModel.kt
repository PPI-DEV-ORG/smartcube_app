package com.ppidev.smartcube.domain.model

import androidx.compose.ui.graphics.painter.Painter

data class EdgeDeviceTypeItem(
    val id: Int,
    val name: String,
    val icon: Painter? = null
)

enum class TypeEdgeDevice(val typeName: String) {
    CAMERA("camera"),
    SENSOR("sensor")
}

enum class TypeDeviceSource(val typeName: String) {
    USB("usb"),
    RTSP("rtsp"),
    IP("ip"),
}

data class DeviceSourceType(
    val id: Int,
    val name: String,
)

val ListEdgeDeviceTypeItems = listOf(
    EdgeDeviceTypeItem(id = 0, name = TypeEdgeDevice.CAMERA.typeName),
    EdgeDeviceTypeItem(id = 1, name = TypeEdgeDevice.SENSOR.typeName)
)

val ListSourceDeviceType = listOf(
    DeviceSourceType(id = 0, name = TypeDeviceSource.USB.typeName),
    DeviceSourceType(id = 1, name = TypeDeviceSource.RTSP.typeName),
    DeviceSourceType(id = 2, name = TypeDeviceSource.IP.typeName),
)