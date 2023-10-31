package com.ppidev.smartcube.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(

    @SerialName("accessToken")
    val accessToken: String
)