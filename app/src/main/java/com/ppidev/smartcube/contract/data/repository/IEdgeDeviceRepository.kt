package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.data.remote.dto.CreateEdgeDeviceDto
import com.ppidev.smartcube.data.remote.dto.DetailEdgeDeviceDto
import com.ppidev.smartcube.data.remote.dto.EdgeDeviceSensorDto
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import com.ppidev.smartcube.utils.ResponseApp

interface IEdgeDeviceRepository {
    suspend fun getEdgeDevicesInfoByEdgeServerId(edgeServerId: UInt): ResponseApp<EdgeDevicesInfoDto?>

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

    suspend fun getDetailEdgeDevice(
        edgeServerId: UInt,
        edgeDeviceId: UInt
    ): ResponseApp<DetailEdgeDeviceDto?>

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

    suspend fun getEdgeDeviceSensor(
        edgeServerId: UInt,
        edgeDeviceId: UInt,
        startDate: String,
        endDate: String
    ): ResponseApp<List<EdgeDeviceSensorDto>?>
}