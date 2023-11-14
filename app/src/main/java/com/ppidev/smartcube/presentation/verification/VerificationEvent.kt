package com.ppidev.smartcube.presentation.verification

import com.ppidev.smartcube.presentation.register.RegisterEvent


sealed class VerificationEvent {
    data class OnVerificationCodeChange(val code: String) : VerificationEvent()
    data class HandleVerification(
        val email: String,
        val callback : () -> Unit
    ) : VerificationEvent()
    object HandleCloseDialog : VerificationEvent()



}