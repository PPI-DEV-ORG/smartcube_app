package com.ppidev.smartcube.domain.use_case.edge_device

import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IEdgeDeviceRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IViewEdgeDeviceUseCase
import com.ppidev.smartcube.data.remote.dto.DetailEdgeDeviceDto
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewEdgeDeviceUseCase @Inject constructor(private val edgeDeviceRepository: Lazy<IEdgeDeviceRepository>): IViewEdgeDeviceUseCase {
    override suspend fun invoke(
        edgeServerId: UInt,
        edgeDeviceId: UInt
    ): Flow<Resource<ResponseApp<DetailEdgeDeviceDto?>>> = flow {
        emit(Resource.Loading())
        emit(getDetailEdgeDevice(edgeServerId, edgeDeviceId))
    }

    private suspend fun getDetailEdgeDevice(edgeServerId: UInt, edgeDeviceId: UInt): Resource<ResponseApp<DetailEdgeDeviceDto?>> {
        try {
            val response = edgeDeviceRepository.get().getDetailEdgeDevice(
                edgeServerId = edgeServerId,
                edgeDeviceId = edgeDeviceId
            )

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