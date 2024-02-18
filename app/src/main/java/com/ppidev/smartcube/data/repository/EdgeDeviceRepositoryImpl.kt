package com.ppidev.smartcube.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ppidev.smartcube.contract.data.repository.IEdgeDeviceRepository
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.dto.CreateEdgeDeviceDto
import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import com.ppidev.smartcube.data.remote.dto.DetailEdgeDeviceDto
import com.ppidev.smartcube.data.remote.dto.EdgeDeviceSensorDto
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.ResponseApp
import retrofit2.HttpException
import javax.inject.Inject

class EdgeDeviceRepositoryImpl @Inject constructor(
    private val smartCubeApi: SmartCubeApi
) : IEdgeDeviceRepository {
    override suspend fun getEdgeDevicesInfoByEdgeServerId(edgeServerId: UInt): ResponseApp<EdgeDevicesInfoDto?> {
        try {
            val response = smartCubeApi.getEdgeDevicesByEdgeServerId(edgeServerId)

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
        sourceAddress: String,
        assignedModelType: UInt,
        assignedModelIndex: UInt,
        additionalInfo: String
    ): ResponseApp<CreateEdgeDeviceDto?> {
        try {
            val response = smartCubeApi.createEdgeDevice(
                edgeServerId = edgeServerId,
                vendorName = vendorName,
                vendorNumber = vendorNumber,
                type = type,
                sourceType = sourceType,
                sourceAddress = sourceAddress,
                additionalInfo = additionalInfo,
                assignedModelType = assignedModelType,
                assignedModelIndex = assignedModelIndex
            )

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<CreateEdgeDeviceDto?>>() {}.type
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
        } catch (e: HttpException) {
            return ResponseApp(
                status = false,
                statusCode = EExceptionCode.RepositoryError.code,
                message = e.localizedMessage ?: "Repository Error",
                data = null
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

    override suspend fun getDetailEdgeDevice(
        edgeServerId: UInt,
        edgeDeviceId: UInt
    ): ResponseApp<DetailEdgeDeviceDto?> {
        try {
            val response = smartCubeApi.getEdgeDeviceById(
                edgeServerId = edgeServerId,
                edgeDeviceId = edgeDeviceId
            )

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<String?>>() {}.type
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

    override suspend fun updateEdgeDevice(
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
    ): ResponseApp<String?> {
        try {
            val response = smartCubeApi.updateEdgeDevice(
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

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<String?>>() {}.type
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
                data = "Success"
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

    override suspend fun getEdgeDeviceSensor(
        edgeServerId: UInt,
        edgeDeviceId: UInt,
        startDate: String,
        endDate: String
    ): ResponseApp<List<EdgeDeviceSensorDto>?> {
        try {
            val response = smartCubeApi.readSensorData(
                edgeServerId = edgeServerId,
                edgeDeviceId = edgeDeviceId,
                startDate = startDate,
                endDate = endDate
            )

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<List<EdgeDeviceSensorDto>?>>() {}.type
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
}