package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.data.remote.dto.UserDto
import com.ppidev.smartcube.utils.ResponseApp

interface IUserRepository {
    suspend fun updateFcmToken(fcmToken: String): ResponseApp<Any?>
    suspend fun getUserProfile(): ResponseApp<UserDto?>
}