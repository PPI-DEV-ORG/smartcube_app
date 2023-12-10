package com.ppidev.smartcube.presentation.notification.detail

sealed class NotificationDetailEvent {
    data class GetDetailNotification(val notificationId: UInt): NotificationDetailEvent()
}