package com.ppidev.smartcube.presentation.register

data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isShowPassword: Boolean = false,
    val isShowConfirmPassword: Boolean = false,
    val isLoading: Boolean = false,
    val error: RegisterError
) {
    data class RegisterError(
        val username: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val message: String = ""
    )
}