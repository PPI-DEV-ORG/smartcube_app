package com.ppidev.smartcube.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isShowPassword: Boolean = false,
    val isLoginLoading: Boolean = false,
    val error: LoginError = LoginError("", "", "")
) {
    data class LoginError(
        val email: String = "",
        val password: String = "",
        val message: String = ""
    )

}