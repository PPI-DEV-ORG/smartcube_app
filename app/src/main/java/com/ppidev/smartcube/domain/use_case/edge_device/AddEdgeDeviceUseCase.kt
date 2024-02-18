package com.ppidev.smartcube.domain.use_case.edge_device

import com.ppidev.smartcube.contract.data.repository.IEdgeDeviceRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IAddEdgeDevicesUseCase
import com.ppidev.smartcube.data.remote.dto.CreateEdgeDeviceDto
import com.ppidev.smartcube.presentation.edge_device.form_add.FormAddEdgeDeviceState
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddEdgeDeviceUseCase @Inject constructor(
    private val edgeDeviceRepository: Lazy<IEdgeDeviceRepository>
) : IAddEdgeDevicesUseCase {
    override suspend fun invoke(
        edgeServerId: UInt,
        vendorName: String,
        vendorNumber: String,
        type: String,
        sourceType: String,
        sourceAddress: String,
        assignedModelType: UInt,
        assignedModelIndex: UInt,
        additionalInfo: String
    ): Flow<Resource<CreateEdgeDeviceDto?, Any>> = flow {
        emit(Resource.Loading())
        emit(
            addNewEdgeDevice(
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
        )
    }

    private suspend fun addNewEdgeDevice(
        edgeServerId: UInt,
        vendorName: String,
        vendorNumber: String,
        type: String,
        sourceType: String,
        sourceAddress: String,
        assignedModelType: UInt,
        assignedModelIndex: UInt,
        additionalInfo: String
    ): Resource<CreateEdgeDeviceDto?, Any> {
        try {
            val response = edgeDeviceRepository.get().addEdgeDevices(
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

            return Resource.Success(response.message, response.data)
        } catch (e: Exception) {
            return Resource.Error(
                EExceptionCode.UseCaseError.code,
                e.message ?: "Something wrong"
            )
        }
    }
}