package com.ppidev.smartcube.data.repository

import android.app.Application
import android.util.Log
import com.ppidev.smartcube.common.Response
import com.ppidev.smartcube.contract.data.repository.INotificationRepository
import com.ppidev.smartcube.data.remote.api.SmartCubeApi
import com.ppidev.smartcube.data.remote.dto.NotificationDto
import java.lang.Exception
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: SmartCubeApi,
    private val appContext: Application
) : INotificationRepository {
    override suspend fun getAllNotifications(): Response<List<NotificationDto>> {

        val response = api.getListNotifications()
        Log.d("NOTIF_", response.toString())
        return response
    }

    override suspend fun getDetailNotification(notificationId: UInt): NotificationDto {
        TODO("Not yet implemented")
    }
}