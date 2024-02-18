package com.ppidev.smartcube.domain.use_case.edge_device

import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IEdgeDeviceRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IListEdgeDevicesByEdgeServerIdUseCase
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListEdgeDevicesByListEdgeServerIdUseCase @Inject constructor(
    private val edgeDeviceRepository: Lazy<IEdgeDeviceRepository>
) : IListEdgeDevicesByEdgeServerIdUseCase {
    override suspend fun invoke(edgeServerId: UInt): Flow<Resource<ResponseApp<EdgeDevicesInfoDto?>>>  = flow {
        emit(Resource.Loading())
        emit(getEdgeDevicesInfo(edgeServerId))
    }

    private suspend fun getEdgeDevicesInfo(edgeServerId: UInt): Resource<ResponseApp<EdgeDevicesInfoDto?>> {
        try {
            val response = edgeDeviceRepository.get().getEdgeDevicesInfoByEdgeServerId(edgeServerId)

            if (!response.status) {
                return Resource.Error(
                    response.statusCode,
                    response.message
                )
            }

            return Resource.Success(response)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }
}