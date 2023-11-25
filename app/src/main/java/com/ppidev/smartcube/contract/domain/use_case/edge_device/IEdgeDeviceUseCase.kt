package com.ppidev.smartcube.contract.domain.use_case.edge_device

import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.CreateEdgeDeviceDto
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import kotlinx.coroutines.flow.Flow

interface IEdgeDevicesInfoUseCase {
    suspend fun invoke(edgeServerId: UInt): Flow<Resource<ResponseApp<EdgeDevicesInfoDto?>>>
}

interface IAddEdgeDevicesUseCase {
    suspend fun invoke(
        edgeServerId: UInt,
        vendorName: String,
        vendorNumber: String,
        type: String,
        sourceType: String,
        devSourceId: String,
        rtspSourceAddress: String,
        assignedModelType: UInt,
        assignedModelIndex: UInt,
        additionalInfo: String
    ): Flow<Resource<ResponseApp<CreateEdgeDeviceDto?>>>
}