package com.ppidev.smartcube.domain.model

data class DeviceType(
    val id: Int,
    val name: String
)

enum class TypeEdgeDevice(val typeName: String) {
    CAMERA("camera"),
    SENSOR("sensor")
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
    DeviceSourceType(id = 0, name = "usb"),
    DeviceSourceType(id = 0, name = "lan")
)