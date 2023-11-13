package com.ppidev.smartcube.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    @SerialName("accessToken")
    val accessToken: String
)

@Serializable
data class RegisterDto(
    @SerialName("id")
    val id: UInt,

    @SerialName("username")
    val username: String,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String? = null,

    @SerialName("isVerified")
    val isVerified: Boolean,

    @SerialName("verificationCode")
    val verificationCode: String,
)

@Serializable
data class VerificationDto(
   @SerialName("id")
    val id: UInt,

    @SerialName("username")
    val username: String,

    @SerialName("email")
    val email : String,

    @SerialName("password")
    val password: String? = null,

    @SerialName("resetToken")
    val resetToken : String? = null,

    @SerialName("isVerified")
    val isVerified : Boolean,

    @SerialName("verificationCode")
    val verificationCode : String? = null,

    @SerialName("fcmRegistrationToken")
    val fcmRegistrationToken : String? = null
)