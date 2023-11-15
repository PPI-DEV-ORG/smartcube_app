package com.ppidev.smartcube.presentation.notification

import com.ppidev.smartcube.domain.model.NotificationModel

data class NotificationState(
    val notifications: List<NotificationModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)