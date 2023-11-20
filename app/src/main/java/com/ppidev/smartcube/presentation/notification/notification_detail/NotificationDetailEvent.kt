package com.ppidev.smartcube.presentation.notification.notification_detail

sealed class NotificationDetailEvent {
    data class GetDetailNotification(val notificationId: UInt): NotificationDetailEvent()
}