package com.ppidev.smartcube.domain.model

data class NotificationModel(
    val id: Int,
    val imageUrl: String,
    val isViewed: Boolean,
    val description: String,
    val title: String
)