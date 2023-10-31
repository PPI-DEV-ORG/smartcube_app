package com.ppidev.smartcube.contract.data.remote.service

import com.ppidev.smartcube.data.remote.dto.FcmMessage

interface IMessagingService<T> {
    fun handleOnMessageReceived(messages: T)
    fun showNotification(data: FcmMessage)
    fun setupMessagingService()
}