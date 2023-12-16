package com.ppidev.smartcube.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    @SerializedName("accessToken")
    val accessToken: String
)

@Serializable
data class RegisterDto(
    @SerializedName("id")
    val id: UInt,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("isVerified")
    val isVerified: Boolean,

    @SerializedName("verificationCode")
    val verificationCode: String,
)

@Serializable
data class VerificationDto(
   @SerializedName("id")
    val id: UInt,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email : String,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("resetToken")
    val resetToken : String? = null,

    @SerializedName("isVerified")
    val isVerified : Boolean,

    @SerializedName("verificationCode")
    val verificationCode : String? = null,

    @SerializedName("fcmRegistrationToken")
    val fcmRegistrationToken : String? = null,
)

data class ResetPasswordRequestDto(
    @SerializedName("data")
    val data: String
)