package com.ppidev.smartcube.domain.model

import androidx.compose.ui.graphics.painter.Painter

data class DeviceType(
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
    LAN("lan")
}

data class DeviceSourceType(
    val id: Int,
    val name: String,
)

val ListDeviceType = listOf<DeviceType>(
    DeviceType(id = 0, name = TypeEdgeDevice.CAMERA.typeName),
    DeviceType(id = 1, name = TypeEdgeDevice.SENSOR.typeName)
)

val ListSourceDeviceType = listOf<DeviceSourceType>(
    DeviceSourceType(id = 0, name = TypeDeviceSource.USB.typeName),
    DeviceSourceType(id = 1, name = TypeDeviceSource.RTSP.typeName),
    DeviceSourceType(id = 2, name = TypeDeviceSource.IP.typeName),
    DeviceSourceType(id = 3, name = TypeDeviceSource.LAN.typeName)
)