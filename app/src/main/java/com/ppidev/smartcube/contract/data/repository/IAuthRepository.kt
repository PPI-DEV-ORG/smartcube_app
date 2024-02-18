package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.data.remote.dto.LoginDto
import com.ppidev.smartcube.data.remote.dto.RegisterDto
import com.ppidev.smartcube.data.remote.dto.VerificationDto
import com.ppidev.smartcube.utils.ResponseApp

interface IAuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): ResponseApp<LoginDto?>

    suspend fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        fcmRegistrationToken: String
    ): ResponseApp<RegisterDto?>

    suspend fun verification(
        email: String,
        verificationCode: String,
    ): ResponseApp<VerificationDto?>

    suspend fun resetPasswordRequest(email: String): ResponseApp<String?>

    suspend fun changePassword(
        resetToken: String,
        newPassword: String, confirmNewPassword: String
    ): ResponseApp<Boolean?>
}