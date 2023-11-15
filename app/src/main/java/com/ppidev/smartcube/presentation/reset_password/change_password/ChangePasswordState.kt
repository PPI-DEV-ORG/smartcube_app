package com.ppidev.smartcube.presentation.reset_password.change_password

data class ChangePasswordState(
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isShowPassword: Boolean = false,
    val isShowConfirmPassword: Boolean = false,
    val isShowDialog: Boolean = false,
    val error: ErrorChangePasswordState = ErrorChangePasswordState()
)  {
    data class ErrorChangePasswordState(
        val message: String = "",
        val password: String = "",
        val confirmPassword: String = ""
    )
}