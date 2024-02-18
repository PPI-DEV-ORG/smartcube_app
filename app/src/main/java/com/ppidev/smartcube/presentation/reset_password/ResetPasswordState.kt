package com.ppidev.smartcube.presentation.reset_password

data class ResetPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val isShowDialog: Boolean = false,
    val error: RequestLinkResetPasswordError = RequestLinkResetPasswordError()
) {
    data class RequestLinkResetPasswordError(
        val email: String = "",
        val message: String = ""
    )
}