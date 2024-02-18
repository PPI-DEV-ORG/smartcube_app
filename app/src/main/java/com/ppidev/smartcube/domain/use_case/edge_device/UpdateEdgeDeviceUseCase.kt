package com.ppidev.smartcube.domain.use_case.edge_device

import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IEdgeDeviceRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IUpdateEdgeDeviceUseCase
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateEdgeDeviceUseCase @Inject constructor(
    private val edgeDeviceRepository: Lazy<IEdgeDeviceRepository>
) : IUpdateEdgeDeviceUseCase {
    override suspend fun invoke(
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
    ): Flow<Resource<ResponseApp<String?>>> = flow {
        emit(Resource.Loading())
        emit(
            updateEdgeDevice(
                edgeServerId,
                edgeDeviceId,
                vendorName,
                vendorNumber,
                type,
                sourceType,
                sourceAddress,
                assignedModelType,
                assignedModelIndex,
                additionalInfo
            )
        )
    }

    private suspend fun updateEdgeDevice(
        edgeServerId: UInt,
        edgeDeviceId: UInt,
        vendorName: String,
        vendorNumber: String,
        type: String,
        sourceType: String,
        sourceAddress: String,
        assignedModelType: UInt,
        assignedModelIndex: UInt,
        additionalInfo: String
    ): Resource<ResponseApp<String?>> {
        try {
            val response = edgeDeviceRepository.get().updateEdgeDevice(
                edgeDeviceId,
                edgeServerId,
                vendorName,
                vendorNumber,
                type,
                sourceType,
                sourceAddress,
                assignedModelType,
                assignedModelIndex,
                additionalInfo
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