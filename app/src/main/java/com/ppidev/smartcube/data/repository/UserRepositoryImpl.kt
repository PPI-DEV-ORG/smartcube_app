package com.ppidev.smartcube.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.repository.IUserRepository
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.dto.LoginDto
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: SmartCubeApi
) : IUserRepository {
    override suspend fun updateFcmToken(fcmToken: String): ResponseApp<Any?> {
        try {
            val response = api.updateFcmToken(fcmToken)

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<Any?>>() {}.type
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