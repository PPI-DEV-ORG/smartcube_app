package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.common.ResponseApp
import com.ppidev.smartcube.data.remote.dto.LoginDto
import com.ppidev.smartcube.data.remote.dto.RegisterDto

interface IAuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): ResponseApp<LoginDto?>

    suspend fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ResponseApp<RegisterDto?>

    suspend fun resetPasswordRequest(email: String): ResponseApp<String?>

    suspend fun changePassword(
        resetToken: String,
        newPassword: String, confirmNewPassword: String
    ): ResponseApp<Boolean?>
}