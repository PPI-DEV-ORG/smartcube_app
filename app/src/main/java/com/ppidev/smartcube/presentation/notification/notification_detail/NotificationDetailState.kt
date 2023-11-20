package com.ppidev.smartcube.presentation.notification.notification_detail

import com.ppidev.smartcube.domain.model.NotificationModel

data class NotificationDetailState(
    val notificationModel: NotificationModel = NotificationModel(
        id = 0,
        imageUrl = "",
        isViewed = false,
        description = "",
        title = ""
    ),
    val isLoading: Boolean = false,
    val error: String = ""
)