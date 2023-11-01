package com.ppidev.smartcube.presentation.login

sealed class LoginEvent {
    object HandleLogin : LoginEvent()

    data class OnEmailChange(val str: String) : LoginEvent()

    data class OnPasswordChange(val str: String) : LoginEvent()

    object ToggleShowPassword: LoginEvent()

    object ToRegisterScreen: LoginEvent()
}
