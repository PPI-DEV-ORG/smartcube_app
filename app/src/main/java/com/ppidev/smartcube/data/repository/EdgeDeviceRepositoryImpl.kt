package com.ppidev.smartcube.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IEdgeDeviceRepository
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.dto.CreateEdgeDeviceDto
import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import javax.inject.Inject

class EdgeDeviceRepositoryImpl @Inject constructor(
    private val smartCubeApi: SmartCubeApi
) : IEdgeDeviceRepository {
    override suspend fun getEdgeDevicesInfo(edgeServerId: UInt): ResponseApp<EdgeDevicesInfoDto?> {
        try {
            val response = smartCubeApi.getEdgeDevices(edgeServerId)

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<CreateEdgeServerDto?>>() {}.type
                )
            }

            val responseBody = response.body()
                ?: return ResponseApp(
                    status = response.isSuccessful,
                    statusCode = response.code(),
                    message = response.message(),
                    data = null
                )

            return ResponseApp(
                status = responseBody.status,
                statusCode = response.code(),
                message = responseBody.message,
                data = responseBody.data
            )
        } catch (e: Exception) {
            return ResponseApp(
                status = false,
                statusCode = EExceptionCode.RepositoryError.code,
                message = e.message ?: "Repository Error",
                data = null
            )
        }
    }

    override suspend fun addEdgeDevices(
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
    ): ResponseApp<CreateEdgeDeviceDto?> {
        try {
            val response = smartCubeApi.createEdgeDevice(
                edgeServerId,
                vendorName,
                vendorNumber,
                type,
                sourceType,
                devSourceId,
                rtspSourceAddress,
                assignedModelType,
                assignedModelIndex,
                additionalInfo
            )

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<CreateEdgeServerDto?>>() {}.type
                )
            }

            val responseBody = response.body()
                ?: return ResponseApp(
                    status = response.isSuccessful,
                    statusCode = response.code(),
                    message = response.message(),
                    data = null
                )

            return ResponseApp(
                status = responseBody.status,
                statusCode = response.code(),
                message = responseBody.message,
                data = responseBody.data
            )
        } catch (e: Exception) {
            return ResponseApp(
                status = false,
                statusCode = EExceptionCode.RepositoryError.code,
                message = e.message ?: "Repository Error",
                data = null
            )
        }
    }

    override suspend fun startEdgeDevice(
        edgeServerId: UInt,
        processIndex: Int
    ): ResponseApp<out Any?> {
        try {
            val response = smartCubeApi.startEdgeDevice(edgeServerId, processIndex)

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<out Any?>>() {}.type
                )
            }

            val responseBody = response.body()
                ?: return ResponseApp(
                    status = response.isSuccessful,
                    statusCode = response.code(),
                    message = response.message(),
                    data = null
                )

            return ResponseApp(
                status = responseBody.status,
                statusCode = response.code(),
                message = responseBody.message,
                data = responseBody.data
            )

        } catch (e: Exception) {
            return ResponseApp(
                status = false,
                statusCode = EExceptionCode.RepositoryError.code,
                message = e.message ?: "Repository Error",
                data = null
            )
        }
    }

    override suspend fun restartEdgeDevice(
        edgeServerId: UInt,
        processIndex: Int
    ): ResponseApp<out Any?> {
        try {
            val response = smartCubeApi.restartEdgeDevice(edgeServerId, processIndex)

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<out Any?>>() {}.type
                )
            }

            val responseBody = response.body()
                ?: return ResponseApp(
                    status = response.isSuccessful,
                    statusCode = response.code(),
                    message = response.message(),
                    data = null
                )

            return ResponseApp(
                status = responseBody.status,
                statusCode = response.code(),
                message = responseBody.message,
                data = responseBody.data
            )

        } catch (e: Exception) {
            return ResponseApp(
                status = false,
                statusCode = EExceptionCode.RepositoryError.code,
                message = e.message ?: "Repository Error",
                data = null
            )
        }
    }

    override suspend fun getDetailEdgeDevice(edgeDeviceId: UInt) {
    }
}