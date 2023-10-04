package com.ppidev.smartcube.data.local.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class TokenAppEntity(
    val accessToken: String = "",
    val fcmToken: String = ""
)
