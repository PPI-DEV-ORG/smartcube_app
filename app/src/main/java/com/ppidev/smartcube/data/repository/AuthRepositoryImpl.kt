package com.ppidev.smartcube.data.repository

import com.ppidev.smartcube.common.Response
import com.ppidev.smartcube.contract.data.local.storage.ITokenAppDataStorePref
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.dto.LoginDto
import com.ppidev.smartcube.data.remote.dto.RegisterDto
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: SmartCubeApi,
    private val tokenAppDataStorePref: ITokenAppDataStorePref<TokenAppEntity>
) : IAuthRepository {
    override suspend fun login(email: String, password: String): Response<LoginDto?> {
        val response = api.login(email, password)

        if (!response.status) {
            return response
        }

        return try {
            response.data?.let { tokenAppDataStorePref.updateAccessToken(it.accessToken) }

            response
        } catch (e: Exception) {
            Response<LoginDto?>(
                status = false,
                statusCode = -1,
                message = "Failed Save Token",
                data = null
            )
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Response<RegisterDto?> {
        val response = api.register(username, email, password, confirmPassword)
        return try {
            response
        } catch (e: Exception) {
            Response<RegisterDto?>(
                status = false,
                statusCode = -1,
                message = "Something wrong!",
                data = null
            )
        }
    }

    override suspend fun resetPasswordRequest(): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(): Response<String> {
        TODO("Not yet implemented")
    }
}