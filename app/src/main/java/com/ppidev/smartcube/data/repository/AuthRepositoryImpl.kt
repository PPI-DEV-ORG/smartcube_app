package com.ppidev.smartcube.data.repository

import android.util.Log
import com.ppidev.smartcube.common.Response
import com.ppidev.smartcube.contract.data.local.storage.ITokenAppDataStorePref
import com.ppidev.smartcube.contract.data.repository.IAuthRepository
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.dto.LoginDto
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

        Log.d("RESPONSE", response.toString())

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

    override suspend fun resetPasswordRequest(): Response<String> {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(): Response<String> {
        TODO("Not yet implemented")
    }
}