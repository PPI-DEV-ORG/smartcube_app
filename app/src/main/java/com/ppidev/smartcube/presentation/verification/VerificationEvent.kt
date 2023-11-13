package com.ppidev.smartcube.presentation.verification


sealed class VerificationEvent {
    data class OnVerificationCodeChange(val code: String) : VerificationEvent()
    object HandleVerification : VerificationEvent()
    object HandleCloseDialog : VerificationEvent()

}