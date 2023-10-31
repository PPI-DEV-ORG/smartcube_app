package com.ppidev.smartcube.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.domain.use_case.auth.ILoginUseCase
import com.ppidev.smartcube.di.NavigationService
import com.ppidev.smartcube.ui.Screen
import com.ppidev.smartcube.utils.validateEmail
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: Lazy<ILoginUseCase>,
    private val navigationService: Lazy<NavigationService>
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.HandleLogin -> {
                handleLogin()
            }

            LoginEvent.ToggleShowPassword -> {
                state = state.copy(
                    isShowPassword = !state.isShowPassword
                )
            }

            is LoginEvent.OnEmailChange -> {
                val str = event.str
                onEmailChange(str)
            }

            is LoginEvent.OnPasswordChange -> {
                val str = event.str
                onPasswordChange(str)
            }
        }
    }

    private fun handleLogin() {
        loginUseCase.get().invoke(state.email, state.password).onEach {
            when (it) {
                is Resource.Loading -> {
                    setIsLoading(true)
                }

                is Resource.Success -> {
                    setIsLoading(false)
                    state = state.copy(
                        error = LoginState.LoginError(
                            email = state.error.email,
                            password = state.error.password,
                            message = ""
                        )
                    )

                    navigationService.get().navController.navigate(Screen.Dashboard.screenRoute)
                }

                is Resource.Error -> {
                    setIsLoading(false)
                    state = state.copy(
                        error = LoginState.LoginError(
                            email = state.error.email,
                            password = state.error.password,
                            message = it.message ?: ""
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun onEmailChange(str: String) {
        state = if (!validateEmail(str)) {
            state.copy(
                error = LoginState.LoginError(
                    email = "Email is Not Valid",
                    password = state.error.password,
                    message = state.error.message
                )
            )
        } else {
            state.copy(
                error = LoginState.LoginError(
                    email = "",
                    password = state.error.password,
                    message = state.error.message
                )
            )
        }

        state = state.copy(
            email = str
        )
    }

    private fun onPasswordChange(str: String) {
        viewModelScope.launch {
            state = state.copy(
                password = str
            )
        }
    }

    private fun setIsLoading(status: Boolean) {
        viewModelScope.launch {
            state = state.copy(
                isLoginLoading = status
            )
        }
    }
}