package com.ppidev.smartcube.presentation.reset_password.change_password

import com.ppidev.smartcube.presentation.login.LoginEvent
import com.ppidev.smartcube.presentation.reset_password.ResetPasswordEvent

sealed class ChangePasswordEvent {
    data class OnChangePassword(val str: String) : ChangePasswordEvent()

    data class OnChangeConfirmPassword(val str: String) : ChangePasswordEvent()

    object ToggleShowPassword: ChangePasswordEvent()
    object ToggleShowConfirmPassword: ChangePasswordEvent()

    data class HandleChangePassword(val resetToken: String, val callback: () -> Unit) : ChangePasswordEvent()


    data class HandleCloseDialog(val callback: () -> Unit) : ChangePasswordEvent()
}