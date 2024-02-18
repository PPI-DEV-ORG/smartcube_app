package com.ppidev.smartcube.data.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.ppidev.smartcube.contract.data.repository.IEdgeServerRepository
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.dto.CreateEdgeServerDto
import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto
import com.ppidev.smartcube.data.remote.dto.InvitationCodeDto
import com.ppidev.smartcube.data.remote.dto.JoinServerDto
import com.ppidev.smartcube.utils.EExceptionCode
import com.ppidev.smartcube.utils.ResponseApp
import javax.inject.Inject

class EdgeServerRepositoryImpl @Inject constructor(
    private val smartCubeApi: SmartCubeApi
) : IEdgeServerRepository {
    override suspend fun addEdgeServer(
        name: String,
        vendor: String,
        description: String
    ): ResponseApp<CreateEdgeServerDto?> {
        try {
            val response = smartCubeApi.createEdgeServer(
                name = name,
                vendor = vendor,
                description = description
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

    override suspend fun listEdgeServer(): ResponseApp<List<EdgeServerItemDto>> {
        try {
            val response = smartCubeApi.getListEdgeServer()
            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<List<EdgeServerItemDto>?>>() {}.type
                )
            }

            val responseBody = response.body()
                ?: return ResponseApp(
                    status = response.isSuccessful,
                    statusCode = response.code(),
                    message = response.message(),
                    data = emptyList()
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
                data = emptyList()
            )
        }
    }

    override suspend fun getInvitationCode(edgeServerId: UInt): ResponseApp<InvitationCodeDto?> {
        try {
            val response = smartCubeApi.getInvitationCode(edgeServerId = edgeServerId)
            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<InvitationCodeDto?>>() {}.type
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

    override suspend fun joinInvitationCode(invitationCode: String): ResponseApp<JoinServerDto?> {
        try {
            val postParam = JsonObject()
            postParam.addProperty("invitation_code", invitationCode)

            val response = smartCubeApi.joinInvitationCode(postParam)

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<JoinServerDto?>>() {}.type
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