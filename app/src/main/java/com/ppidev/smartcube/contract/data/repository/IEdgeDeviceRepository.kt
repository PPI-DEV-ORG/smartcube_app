package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.CreateEdgeDeviceDto
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto

interface IEdgeDeviceRepository {
    suspend fun getEdgeDevicesInfo(edgeServerId: UInt): ResponseApp<EdgeDevicesInfoDto?>

    suspend fun addEdgeDevices(
        edgeServerId: UInt,
        vendorName: String,
        vendorNumber: String,
        type: String,
        sourceType: String,
        sourceAddress: String,
        assignedModelType: UInt,
        assignedModelIndex: UInt,
        additionalInfo: String
    ): ResponseApp<CreateEdgeDeviceDto?>

    suspend fun startEdgeDevice(edgeServerId: UInt, processIndex: Int): ResponseApp<out Any?>
    suspend fun restartEdgeDevice(edgeServerId: UInt, processIndex: Int): ResponseApp<out Any?>
    suspend fun getDetailEdgeDevice(edgeServerId: UInt, edgeDeviceId: UInt)
    suspend fun updateEdgeDevice(
        edgeDeviceId: UInt,
        edgeServerId: UInt,
        vendorName: String,
        vendorNumber: String,
        type: String,
        sourceType: String,
        sourceAddress: String,
        assignedModelType: UInt,
        assignedModelIndex: UInt,
        additionalInfo: String
    ): ResponseApp<String?>
}