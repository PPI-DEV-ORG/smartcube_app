package com.ppidev.smartcube.data.remote.dto

import com.ppidev.smartcube.domain.model.ServerStatusModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerStatusDto(
    @SerialName("cpu_temp")
    val cpuTemp: String,

    @SerialName("cpu_usage")
    val cpuUsage: String,

    @SerialName("memory_usage")
    val memoryUsage: String,

    @SerialName("memory_free")
    val memoryFree: String,

    @SerialName("memory_total")
    val memoryTotal: String,

    @SerialName("storage")
    val storage: Storage,

    @SerialName("up_time")
    val upTime: String,

    @SerialName("fan_speed")
    val fanSpeed: String
)

@Serializable
data class Storage(
    @SerialName("partition")
    val partition: String,

    @SerialName("total_space")
    val totalSpace: String,

    @SerialName("used_space")
    val usedSpace: String,

    @SerialName("free_space")
    val freeSpace: String,

    @SerialName("disk_usage")
    val diskUsage: String
)

fun ServerStatusDto.toDeviceStatusModel(): ServerStatusModel {
    return ServerStatusModel(
        cpuTemp = cpuTemp,
        memoryFree = memoryFree,
        storageFree = storage.freeSpace,
        fanSpeed = fanSpeed,
        upTime = upTime
    )
}