package com.ppidev.smartcube.presentation.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isShowPassword: Boolean = false,
    val isShowConfirmPassword: Boolean = false,
    val error: RegisterError
) {
    data class RegisterError(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val message: String = ""
    )
}