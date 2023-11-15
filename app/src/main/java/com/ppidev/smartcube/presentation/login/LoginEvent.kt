package com.ppidev.smartcube.presentation.login

sealed class LoginEvent {
    data class HandleLogin(
        val callback: () -> Unit
    ) : LoginEvent()

    data class OnEmailChange(val str: String) : LoginEvent()

    data class OnPasswordChange(val str: String) : LoginEvent()

    object ToggleShowPassword: LoginEvent()

    data class ToRegisterScreen(
        val callback: () -> Unit
    ): LoginEvent()

    data class ToResetPasswordScreen(
        val callback: () -> Unit
    ): LoginEvent()
}
