package com.ppidev.smartcube.domain.model

import androidx.compose.runtime.Composable
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
    IP("ip")
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
    DeviceSourceType(id = 0, name = TypeDeviceSource.IP.typeName)
)