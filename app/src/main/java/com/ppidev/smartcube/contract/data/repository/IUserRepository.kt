package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.common.ResponseApp

interface IUserRepository {
    suspend fun updateFcmToken(fcmToken: String): ResponseApp<Any?>
}