package com.ppidev.smartcube.contract.service

import com.ppidev.smartcube.data.local.dataclass.NotificationEntity

interface IMessagingService<T> {
    fun handleOnMessageReceived(messages: T)
    fun showNotification(data: NotificationEntity)
    fun onNotificationClicked()
    fun setupMessagingService()
}

