package com.ppidev.smartcube.presentation.notification

sealed class NotificationEvent {
    object GetListNotification: NotificationEvent()

    data class ToNotificationDetail(val callback: (notificationId: UInt) -> Unit): NotificationEvent()
}