package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.data.remote.dto.NotificationDto
import com.ppidev.smartcube.common.ResponseApp
import retrofit2.Response

interface INotificationRepository {
    suspend fun getAllNotifications(): ResponseApp<List<NotificationDto>?>
    suspend fun getDetailNotification(notificationId: UInt): ResponseApp<NotificationDto?>
}