package com.ppidev.smartcube.presentation.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.data.repository.ITokenAppRepository
import com.ppidev.smartcube.contract.domain.use_case.auth.IRegisterUseCase
import com.ppidev.smartcube.utils.validateEmail
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: Lazy<IRegisterUseCase>,
    private val tokenAppRepositoryImpl: ITokenAppRepository
) : ViewModel() {
    var state by mutableStateOf(
        RegisterState(
            error = RegisterState.RegisterError()
        )
    )
        private set

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnEmailChange -> {
                onEmailChange(event.str)
            }

            is RegisterEvent.OnPasswordChange -> {
                onPasswordChange(event.str)
            }

            is RegisterEvent.OnConfirmPasswordChange -> {
                onConfirmPasswordChange(event.str)
            }

            is RegisterEvent.OnUsernameChange -> {
                onUsernameChange(event.str)
            }

            RegisterEvent.HandleRegister -> {
                handleRegister()
            }

            RegisterEvent.ToggleShowPassword -> {
                state = state.copy(
                    isShowPassword = !state.isShowPassword
                )
            }

            RegisterEvent.ToggleShowConfirmPassword -> {
                state = state.copy(
                    isShowConfirmPassword = !state.isShowConfirmPassword
                )
            }

            is RegisterEvent.ToLoginScreen -> {
                event.callback()
            }

            is RegisterEvent.HandleCloseDialog -> {
                state = state.copy(
                    isShowDialog = false
                )
                event.callback()
            }

        }
    }

    private fun handleRegister() {
        viewModelScope.launch {
            val fcmToken = tokenAppRepositoryImpl.getFcmToken().first()

            if (fcmToken.isEmpty()) {
                return@launch
            }

            registerUseCase.get().invoke(
                username = state.username,
                email = state.email,
                password = state.password,
                confirmPassword = state.confirmPassword,
                fcmRegistrationToken = fcmToken
            ).onEach {
                when (it) {
                    is Resource.Loading -> {
                        setLoadingStatus(true)
                    }

                    is Resource.Success -> {
                        setLoadingStatus(false)

                        state = state.copy(
                            isShowDialog = true,
                            username = "",
                            password = "",
                            confirmPassword = ""
                        )
                    }

                    is Resource.Error -> {
                        setLoadingStatus(false)
                        Log.d("REGIS", it.message.toString())
                        state = state.copy(
                            error = RegisterState.RegisterError(
                                message = it.message.toString()
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

    private fun onUsernameChange(str: String) {
        viewModelScope.launch {
            state = state.copy(
                username = str
            )
        }
    }

    private fun onEmailChange(str: String) {
        viewModelScope.launch {

            state = if (!validateEmail(str)) {
                state.copy(
                    error = RegisterState.RegisterError(
                        email = "Email is Not Valid",
                    )
                )
            } else {
                state.copy(
                    error = RegisterState.RegisterError(
                        email = "",
                    )
                )
            }

            state = state.copy(
                email = str
            )
        }
    }

    private fun onPasswordChange(str: String) {
        viewModelScope.launch {

            state = state.copy(
                password = str
            )
        }
    }

    private fun onConfirmPasswordChange(str: String) {
        viewModelScope.launch {
            state = if (state.password != str) {
                state.copy(
                    error = RegisterState.RegisterError(
                        confirmPassword = "Confirm password not same"
                    )
                )
            } else {
                state.copy(
                    error = RegisterState.RegisterError(
                        confirmPassword = ""
                    )
                )
            }

            state = state.copy(
                confirmPassword = str
            )
        }
    }

    private fun setLoadingStatus(status: Boolean) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = status
            )
        }
    }
}