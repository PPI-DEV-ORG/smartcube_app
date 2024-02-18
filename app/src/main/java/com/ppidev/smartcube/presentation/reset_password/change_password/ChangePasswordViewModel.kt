package com.ppidev.smartcube.presentation.reset_password.change_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.contract.domain.use_case.auth.IChangePasswordUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: Lazy<IChangePasswordUseCase>
) : ViewModel() {
    var state by mutableStateOf(ChangePasswordState())
        private set

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.HandleChangePassword -> {
                handleChangePassword(event.resetToken, event.callback)
            }

            is ChangePasswordEvent.HandleCloseDialog -> {
                onToggleDialog(false)
                event.callback()
            }

            is ChangePasswordEvent.OnChangeConfirmPassword -> {
                onConfirmPasswordChange(event.str)
            }

            is ChangePasswordEvent.OnChangePassword -> {
                onPasswordChange(event.str)
            }

            ChangePasswordEvent.ToggleShowConfirmPassword -> setShowConfirmationPassword(status = !state.isShowConfirmPassword)

            ChangePasswordEvent.ToggleShowPassword -> setShowPassword(status = !state.isShowPassword)
        }
    }

    private fun handleChangePassword(resetToken: String, callback: () -> Unit) {
        if (state.password.isEmpty()) {
            state = state.copy(
                error = ChangePasswordState.ErrorChangePasswordState(
                    password = "Password Cannot empty!"
                )
            )
            return
        }

        if (state.confirmPassword.isEmpty()) {
            state = state.copy(
                error = ChangePasswordState.ErrorChangePasswordState(
                    confirmPassword = "Password Cannot empty!"
                )
            )
            return
        }

        if (state.error.password.isNotEmpty() || state.error.confirmPassword.isNotEmpty()) {
            return
        }

        changePasswordUseCase.get().invoke(resetToken, state.password, state.confirmPassword)
            .onEach {
                when (it) {
                    is Resource.Loading -> {
                        setIsLoading(true)
                    }

                    is Resource.Success -> {
                        setIsLoading(false)
                        state = state.copy(
                            isShowDialog = true
                        )

                        callback()
                    }

                    is Resource.Error -> {
                        setIsLoading(false)
                        state = state.copy(
                            error = ChangePasswordState.ErrorChangePasswordState(
                                message = it.message ?: "Failed change password"
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun onToggleDialog(status: Boolean) {
        viewModelScope.launch {
            state = state.copy(
                isShowDialog = status
            )
        }
    }

    private fun onPasswordChange(str: String) {
        viewModelScope.launch {
            if (state.password.isNotEmpty()) {
                state = state.copy(
                    error = ChangePasswordState.ErrorChangePasswordState(
                        password = ""
                    )
                )
            }

            state = state.copy(
                password = str
            )
        }
    }

    private fun onConfirmPasswordChange(str: String) {
        viewModelScope.launch {
            state = if (state.password != str) {
                state.copy(
                    error = ChangePasswordState.ErrorChangePasswordState(
                        confirmPassword = "Confirm password not same"
                    )
                )
            } else {
                state.copy(
                    error = ChangePasswordState.ErrorChangePasswordState(
                        confirmPassword = ""
                    )
                )
            }

            state = state.copy(
                confirmPassword = str
            )
        }
    }

    private fun setIsLoading(status: Boolean) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = status
            )
        }
    }

    private fun setShowPassword(status: Boolean) {
        viewModelScope.launch {
            state = state.copy(
                isShowPassword = status
            )
        }
    }

    private fun setShowConfirmationPassword(status: Boolean) {
        viewModelScope.launch {
            state = state.copy(
                isShowConfirmPassword = status
            )
        }
    }
}

sealed class ChangePasswordEvent {
    data class OnChangePassword(val str: String) : ChangePasswordEvent()
    data class OnChangeConfirmPassword(val str: String) : ChangePasswordEvent()
    object ToggleShowPassword: ChangePasswordEvent()
    object ToggleShowConfirmPassword: ChangePasswordEvent()
    data class HandleChangePassword(val resetToken: String, val callback: () -> Unit) : ChangePasswordEvent()
    data class HandleCloseDialog(val callback: () -> Unit) : ChangePasswordEvent()
}