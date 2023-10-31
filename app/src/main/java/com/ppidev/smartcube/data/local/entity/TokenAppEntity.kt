package com.ppidev.smartcube.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class TokenAppEntity(
    val accessToken: String = "",
    val fcmToken: String = ""
)
