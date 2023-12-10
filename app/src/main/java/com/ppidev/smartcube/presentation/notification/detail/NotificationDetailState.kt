package com.ppidev.smartcube.presentation.notification.detail

import com.ppidev.smartcube.domain.model.NotificationModel

data class NotificationDetailState(
    val notificationModel: NotificationModel?= null,
    val isLoading: Boolean = false,
    val error: String = ""
)