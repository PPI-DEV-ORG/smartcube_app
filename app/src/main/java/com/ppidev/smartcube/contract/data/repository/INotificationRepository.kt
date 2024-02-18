package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.data.remote.dto.NotificationDto
import com.ppidev.smartcube.utils.ResponseApp

interface INotificationRepository {
    suspend fun getAllNotifications(): ResponseApp<List<NotificationDto>?>

    suspend fun getDetailNotification(
        notificationId: UInt,
        edgeServerId: UInt
    ): ResponseApp<NotificationDto?>
}