package com.ppidev.smartcube.presentation.otp


sealed class OtpEvent {
    data class OnOtpChange(val otp: String) : OtpEvent()
    object VerifyOtp : OtpEvent()
}
