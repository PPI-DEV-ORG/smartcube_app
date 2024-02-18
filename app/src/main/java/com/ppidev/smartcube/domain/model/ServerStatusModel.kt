package com.ppidev.smartcube.domain.model

data class ServerStatusModel(
    val cpuTemp: String,
    val memoryFree: String,
    val memoryTotal: String,
    val storageFree: String,
    val upTime: String,
    val fanSpeed: String
)