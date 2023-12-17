package com.ppidev.smartcube.presentation.notification.detail

sealed class NotificationDetailEvent {
    data class GetDetailNotification(val notificationId: UInt, val edgeServerId: UInt): NotificationDetailEvent()
    data class SetOverlayImageStatus(val status: Boolean): NotificationDetailEvent()
}