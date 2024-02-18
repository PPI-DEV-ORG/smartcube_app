package com.ppidev.smartcube.presentation.notification.list

import com.ppidev.smartcube.domain.model.NotificationModel

data class NotificationListState(
    val notifications: List<NotificationModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val notificationId: UInt? = null,
    val detailNotification: NotificationModel? = null,
    val isOpenImageOverlay: Boolean = false
)