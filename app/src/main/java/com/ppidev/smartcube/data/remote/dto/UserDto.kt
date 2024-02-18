package com.ppidev.smartcube.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerializedName("username")
    val userName: String,

    @SerializedName("user_email")
    val userEmail: String,

    @SerializedName("user_avatar")
    val userAvatar: String? = null
)