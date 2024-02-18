package com.ppidev.smartcube.presentation.verification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.contract.domain.use_case.auth.IVerificationUseCase
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val verificationUseCase: Lazy<IVerificationUseCase>
) : ViewModel() {
    var state by mutableStateOf(
        VerificationState(
            error = VerificationState.VerificationError()
        )
    )
        private set

    fun onEvent(event: VerificationEvent) {
        when (event) {
            is VerificationEvent.OnVerificationCodeChange -> {
                onVerificationCodeChange(event.code)
            }

            is VerificationEvent.HandleVerification -> {
                handleVerification(event.email, event.callback)
            }

            VerificationEvent.HandleCloseDialog -> {
                state = state.copy(isShowDialog = false)
            }

            else -> {}
        }
    }

    private fun onVerificationCodeChange(code: String) {
        viewModelScope.launch {
            state = state.copy(verificationCode = code)
        }
    }

    private fun handleVerification(email: String, callback: () -> Unit) {
        verificationUseCase.get().invoke(email, state.verificationCode).onEach {
            when (it) {
                is Resource.Success -> {
                    state = state.copy(isVerificationSuccessful = true, isShowDialog = true)
                    callback()
                }

                is Resource.Error -> {
                    state = state.copy(isVerificationSuccessful = false, isShowDialog = true)
                }

                is Resource.Loading -> {
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}

sealed class VerificationEvent {
    data class OnVerificationCodeChange(val code: String) : VerificationEvent()
    data class HandleVerification(
        val email: String,
        val callback: () -> Unit
    ) : VerificationEvent()

    object HandleCloseDialog : VerificationEvent()
}