package com.ppidev.smartcube.domain.use_case.edge_device

import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IEdgeDeviceRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IRestartEdgeDeviceUseCase
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RestartEdgeDeviceUseCase @Inject constructor(
    private val edgeDeviceRepository: Lazy<IEdgeDeviceRepository>
) : IRestartEdgeDeviceUseCase {
    override suspend fun invoke(
        edgeServerId: UInt,
        processIndex: Int
    ): Flow<Resource<ResponseApp<out Any?>>> = flow {
        emit(Resource.Loading())
        emit(restartDevice(edgeServerId, processIndex))
    }

    private suspend fun restartDevice(
        edgeServerId: UInt,
        processIndex: Int
    ): Resource<ResponseApp<out Any?>> {
        try {
            val response = edgeDeviceRepository.get().restartEdgeDevice(edgeServerId, processIndex)

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