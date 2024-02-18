package com.ppidev.smartcube.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    @field:SerializedName("accessToken")
    val accessToken: String
)

@Serializable
data class RegisterDto(
    @field:SerializedName("id")
    val id: UInt,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("isVerified")
    val isVerified: Boolean,

    @field:SerializedName("verificationCode")
    val verificationCode: String,
)

@Serializable
data class VerificationDto(
    @field:SerializedName("id")
    val id: UInt,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email : String,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("resetToken")
    val resetToken : String? = null,

    @field:SerializedName("isVerified")
    val isVerified : Boolean,

    @field:SerializedName("verificationCode")
    val verificationCode : String? = null,

    @field:SerializedName("fcmRegistrationToken")
    val fcmRegistrationToken : String? = null,
)

data class ResetPasswordRequestDto(
    @field:SerializedName("data")
    val data: String
)