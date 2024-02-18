package com.ppidev.smartcube.utils

enum class ENotificationChannel(
    val id: Int,
    val idName: String,
    val channelName: String,
    val desc: String,
) {
    FCM_CHANNEL(
        id = 1,
        idName = "FCM_Notification",
        channelName = "FCM Notification Channel",
        desc = "FCM Notification Channel for announce object detected",
    ),
    TEST_CHANNEL(
        id = 2,
        idName = "TEST_Notification",
        channelName = "TEST Notification Channel",
        desc = "TEST Notification Channel for TEST",
    )
}