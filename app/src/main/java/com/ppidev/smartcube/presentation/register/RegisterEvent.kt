package com.ppidev.smartcube.presentation.register

import com.ppidev.smartcube.presentation.login.LoginEvent

sealed class RegisterEvent {
    object HandleRegister : RegisterEvent()

    data class OnUsernameChange(val str: String) : RegisterEvent()

    data class OnEmailChange(val str: String) : RegisterEvent()

    data class OnPasswordChange(val str: String) : RegisterEvent()

    data class OnConfirmPasswordChange(val str: String) : RegisterEvent()

    object ToggleShowPassword: RegisterEvent()

    object ToggleShowConfirmPassword: RegisterEvent()

    data class ToLoginScreen(
        val callback: () -> Unit
    ): RegisterEvent()
}