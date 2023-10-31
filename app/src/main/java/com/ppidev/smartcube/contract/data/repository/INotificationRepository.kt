package com.ppidev.smartcube.contract.data.repository

import com.ppidev.smartcube.data.remote.dto.NotificationDto
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.common.Response
import kotlinx.coroutines.flow.Flow

interface INotificationRepository {
    suspend fun getAllNotifications(): Response<List<NotificationDto>>
    suspend fun getDetailNotification(notificationId: UInt): NotificationDto
}