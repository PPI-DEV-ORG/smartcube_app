package com.ppidev.smartcube.domain.model

data class NotificationModel(
    val id: Int,
    val imageUrl: String,
    val isViewed: Boolean,
    val description: String,
    val title: String,
    val createdAt: String,
    val deviceType: String,
    val objectLabel: String? = null,
    val riskLevel: String? = null,
    val edgeDeviceId: UInt,
    val edgeServerId: UInt
)