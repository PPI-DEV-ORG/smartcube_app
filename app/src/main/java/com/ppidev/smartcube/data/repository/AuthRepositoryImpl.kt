package com.ppidev.smartcube.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ppidev.smartcube.common.EExceptionCode
import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.contract.data.local.storage.ITokenAppDataStorePref
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.dto.LoginDto
import com.ppidev.smartcube.data.remote.dto.RegisterDto
import com.ppidev.smartcube.data.remote.dto.VerificationDto
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: SmartCubeApi,
    private val tokenAppDataStorePref: ITokenAppDataStorePref<TokenAppEntity>
) : IAuthRepository {
    override suspend fun login(email: String, password: String): ResponseApp<LoginDto?> {
        try {
            val response = api.login(email, password)

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<LoginDto?>>() {}.type
                )
            }

            val responseBody = response.body()
                ?: return ResponseApp(
                    status = response.isSuccessful,
                    statusCode = response.code(),
                    message = response.message(),
                    data = null
                )

            responseBody.data?.let { data ->
                tokenAppDataStorePref.updateAccessToken(data.accessToken)
            }

            return ResponseApp(
                status = responseBody.status,
                statusCode = response.code(),
                message = responseBody.message,
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

    override suspend fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ResponseApp<RegisterDto?> {
        try {
            val response = api.register(username, email, password, confirmPassword)

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<LoginDto?>>() {}.type
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

    override suspend fun verification(
        email: String,
        verificationCode: String
    ): ResponseApp<VerificationDto?> {
         try {
            val response = api.verification(email,verificationCode)

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<VerificationDto?>>() {}.type
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
        } catch (e:Exception){
            return ResponseApp<VerificationDto?>(
                status = false,
                statusCode = -1,
                message = "Verification Fail",
                data = null
            )
        }
    }

    override suspend fun resetPasswordRequest(email: String): ResponseApp<String?> {
        try {
            val response = api.resetToken(email = email)

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<LoginDto?>>() {}.type
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

    override suspend fun changePassword(
        resetToken: String,
        newPassword: String,
        confirmNewPassword: String
    ): ResponseApp<Boolean?> {
        try {
            val response =  api.changePassword(resetToken, newPassword, confirmNewPassword)
            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.string()
                return Gson().fromJson(
                    errorResponse,
                    object : TypeToken<ResponseApp<LoginDto?>>() {}.type
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