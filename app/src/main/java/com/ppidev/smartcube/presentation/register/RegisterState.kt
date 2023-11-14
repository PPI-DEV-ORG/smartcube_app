package com.ppidev.smartcube.presentation.register

data class RegisterState(
    val error: RegisterError,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isShowPassword: Boolean = false,
    val isShowConfirmPassword: Boolean = false,
    val isLoading: Boolean = false,
    val isShowDialog: Boolean = false,
    val succesToken : String = ""
) {
    data class RegisterError(
        val username: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val message: String = ""
    )
}