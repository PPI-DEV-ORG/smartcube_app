package com.ppidev.smartcube.data.local.dataclass

import android.graphics.Bitmap

data class NotificationEntity(
    val title: String = "title",
    val description: String = "description",
    val bitmapImage: Bitmap? = null
)