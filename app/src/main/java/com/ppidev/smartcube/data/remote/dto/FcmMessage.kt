package com.ppidev.smartcube.data.remote.dto

data class FcmMessage(
    val title: String,
    val description: String,
    val imageUrl: String,
    val notificationId: String
)