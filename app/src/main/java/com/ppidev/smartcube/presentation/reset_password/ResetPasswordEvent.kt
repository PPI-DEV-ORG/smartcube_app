package com.ppidev.smartcube.presentation.reset_password


sealed class ResetPasswordEvent {
    data class OnEmailChange(val str: String) : ResetPasswordEvent()

    data class HandleRequestResetLinkPassword(
        val callback: () -> Unit
    ) : ResetPasswordEvent()


    object HandleCloseDialog : ResetPasswordEvent()
}