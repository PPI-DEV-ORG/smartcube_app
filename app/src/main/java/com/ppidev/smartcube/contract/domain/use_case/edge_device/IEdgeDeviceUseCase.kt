package com.ppidev.smartcube.contract.domain.use_case.edge_device

import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import kotlinx.coroutines.flow.Flow

interface IEdgeDevicesInfoUseCase {
    suspend fun invoke(edgeServerId: UInt) : Flow<Resource<ResponseApp<EdgeDevicesInfoDto?>>>
}