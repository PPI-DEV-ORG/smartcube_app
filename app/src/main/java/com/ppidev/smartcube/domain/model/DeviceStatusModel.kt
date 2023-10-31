package com.ppidev.smartcube.domain.model

data class DeviceStatusModel(
    val cpuTemp: String,
    val memoryFree: String,
    val storageFree: String,
    val upTime: String,
    val fanSpeed: String
)