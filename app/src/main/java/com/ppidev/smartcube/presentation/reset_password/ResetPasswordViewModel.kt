package com.ppidev.smartcube.presentation.reset_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.contract.domain.use_case.auth.IRequestLinkResetPasswordUseCase
import com.ppidev.smartcube.utils.validateEmail
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val requestResetLinkPassword: Lazy<IRequestLinkResetPasswordUseCase>
) : ViewModel() {
    var state by mutableStateOf(ResetPasswordState())
        private set
    fun onEvent(event: ResetPasswordEvent) {
        when (event) {

            is ResetPasswordEvent.HandleRequestResetLinkPassword -> {
                handleRequestLinkResetPassword(event.callback)
            }

            is ResetPasswordEvent.OnEmailChange -> {
                onEmailChange(event.str)
            }

            ResetPasswordEvent.HandleCloseDialog -> {
                handleCloseDialog()
            }
        }
    }


    private fun handleRequestLinkResetPassword(callback: () -> Unit) {
        if (state.email.isEmpty()) {
            state = state.copy(
                error = ResetPasswordState.RequestLinkResetPasswordError(
                    email = "Email Cannot empty!"
                )
            )
            return
        }

        requestResetLinkPassword.get().invoke(state.email).onEach {
            when (it) {
                is Resource.Loading -> {
                    setIsLoading(true)
                }

                is Resource.Success -> {
                    setIsLoading(false)

                    state = state.copy(
                        isShowDialog = true,
                        email = ""
                    )

                    callback()
                }

                is Resource.Error -> {
                    setIsLoading(false)
                    state = state.copy(
                        error = ResetPasswordState.RequestLinkResetPasswordError(
                            message = it.message.toString()
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun onEmailChange(str: String) {
        state = if (!validateEmail(str)) {
            state.copy(
                error = ResetPasswordState.RequestLinkResetPasswordError(
                    email = "Email is Not Valid",
                )
            )
        } else {
            state.copy(
                error = ResetPasswordState.RequestLinkResetPasswordError(
                    email = "",
                )
            )
        }

        state = state.copy(
            email = str
        )
    }

    private fun setIsLoading(status: Boolean) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = status
            )
        }
    }

    private fun handleCloseDialog() {
        viewModelScope.launch {
            state = state.copy(
                isShowDialog = false
            )
        }
    }

}

sealed class ResetPasswordEvent {
    data class OnEmailChange(val str: String) : ResetPasswordEvent()
    data class HandleRequestResetLinkPassword(
        val callback: () -> Unit
    ) : ResetPasswordEvent()
    object HandleCloseDialog : ResetPasswordEvent()
}