package com.ppidev.smartcube.contract.domain.use_case.edge_device

import com.ppidev.smartcube.data.remote.dto.CreateEdgeDeviceDto
import com.ppidev.smartcube.data.remote.dto.DetailEdgeDeviceDto
import com.ppidev.smartcube.data.remote.dto.EdgeDeviceSensorDto
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import kotlinx.coroutines.flow.Flow

interface IListEdgeDevicesByEdgeServerIdUseCase {
    suspend fun invoke(edgeServerId: UInt): Flow<Resource<EdgeDevicesInfoDto?, Any>>
}

interface IViewEdgeDeviceUseCase {
    suspend fun invoke(
        edgeServerId: UInt,
        edgeDeviceId: UInt,
    ): Flow<Resource<DetailEdgeDeviceDto?, Any>>
}

interface IAddEdgeDevicesUseCase {
    suspend fun invoke(
        edgeServerId: UInt,
        vendorName: String,
        vendorNumber: String,
        type: String,
        sourceType: String,
        sourceAddress: String,
        assignedModelType: UInt,
        assignedModelIndex: UInt,
        additionalInfo: String
    ): Flow<Resource<CreateEdgeDeviceDto?, Any>>
}

interface IStartEdgeDeviceUseCase {
    suspend fun invoke(
        edgeServerId: UInt,
        processIndex: Int
    ): Flow<Resource<Any?, Any>>
}

interface IRestartEdgeDeviceUseCase {
    suspend fun invoke(
        edgeServerId: UInt,
        processIndex: Int
    ): Flow<Resource<Any?, Any>>
}

interface IUpdateEdgeDeviceUseCase {
    suspend fun invoke(
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
    ): Flow<Resource<String?, Any>>
}

interface IReadEdgeDeviceSensorUseCase {
    suspend fun invoke(
        edgeServerId: UInt,
        edgeDeviceId: UInt,
        startDate: String,
        endDate: String
    ): Flow<Resource<List<EdgeDeviceSensorDto>?, Any>>
}
