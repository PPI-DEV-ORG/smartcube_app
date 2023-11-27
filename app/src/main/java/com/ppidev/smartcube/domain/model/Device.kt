package com.ppidev.smartcube.domain.model

data class DeviceType(
    val id: Int,
    val name: String
)

data class DeviceSourceType(
    val id: Int,
    val name: String,
)

val ListDeviceType = listOf<DeviceType>(
    DeviceType(id = 0, name = "camera"),
    DeviceType(id = 1, name = "sensor")
)

val ListSourceDeviceType = listOf<DeviceSourceType>(
    DeviceSourceType(id = 0, name = "usb"),
    DeviceSourceType(id = 0, name = "lan")
)