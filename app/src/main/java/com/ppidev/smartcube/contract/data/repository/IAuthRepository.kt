package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.common.Response
import com.ppidev.smartcube.data.remote.dto.LoginDto
import com.ppidev.smartcube.data.remote.dto.RegisterDto

interface IAuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Response<LoginDto?>

    suspend fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Response<RegisterDto?>

    suspend fun resetPasswordRequest(): Response<String>

    suspend fun resetPassword(): Response<String>
}