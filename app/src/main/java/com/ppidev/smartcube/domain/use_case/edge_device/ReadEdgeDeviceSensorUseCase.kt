package com.ppidev.smartcube.domain.use_case.edge_device

import com.ppidev.smartcube.contract.data.repository.IEdgeDeviceRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IReadEdgeDeviceSensorUseCase
import com.ppidev.smartcube.data.remote.dto.EdgeDeviceSensorDto
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReadEdgeDeviceSensorUseCase @Inject constructor(
    private val edgeDeviceRepository: Lazy<IEdgeDeviceRepository>
) : IReadEdgeDeviceSensorUseCase {
    override suspend fun invoke(
        edgeServerId: UInt,
        edgeDeviceId: UInt,
        startDate: String,
        endDate: String
    ): Flow<Resource<List<EdgeDeviceSensorDto>?, Any>> = flow {
        emit(Resource.Loading())
        emit(getEdgeDevicesInfo(edgeServerId, edgeDeviceId, startDate, endDate))
    }

    private suspend fun getEdgeDevicesInfo(
        edgeServerId: UInt,
        edgeDeviceId: UInt,
        startDate: String,
        endDate: String
    ): Resource<List<EdgeDeviceSensorDto>?, Any> {
        try {
            val response = edgeDeviceRepository.get().getEdgeDeviceSensor(
                edgeServerId = edgeServerId,
                edgeDeviceId = edgeDeviceId,
                startDate = startDate,
                endDate = endDate
            )

            if (!response.status) {
                return Resource.Error(
                    response.statusCode,
                    response.message
                )
            }

            return Resource.Success(response.message, response.data)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }
}