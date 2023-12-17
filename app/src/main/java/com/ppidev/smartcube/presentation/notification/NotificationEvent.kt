package com.ppidev.smartcube.presentation.notification

sealed class NotificationEvent {
    object GetListNotification: NotificationEvent()
    data class SetNotificationId(val notificationId: UInt): NotificationEvent()
    data class GetNotificationDetail(val notificationId: UInt, val edgeServerId: UInt): NotificationEvent()
    data class SetOpenImageOverlay(val status: Boolean): NotificationEvent()
}