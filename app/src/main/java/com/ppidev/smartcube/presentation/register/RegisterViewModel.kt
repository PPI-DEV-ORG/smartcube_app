package com.ppidev.smartcube.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.domain.use_case.auth.IRegisterUseCase
//import com.ppidev.smartcube.di.NavigationService
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.utils.validateEmail
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: Lazy<IRegisterUseCase>
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

            RegisterEvent.HandleRegister -> {}

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
        }
    }

    private fun handleRegister() {
        registerUseCase.get().invoke(username = state.username, email = state.email, password = state.password, confirmPassword = state.confirmPassword).onEach {
            when(it) {
                is Resource.Loading ->{}
                is Resource.Success -> {}
                is Resource.Error -> {}
            }
        }
    }

    private fun onUsernameChange(str: String) {
        state = state.copy(
            username = str
        )
    }

    private fun onEmailChange(str: String) {
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

    private fun onPasswordChange(str: String) {
        state = state.copy(
            password = str
        )
    }

    private fun onConfirmPasswordChange(str: String) {
        state = state.copy(
            confirmPassword = str
        )
    }
}