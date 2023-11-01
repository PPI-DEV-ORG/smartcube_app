package com.ppidev.smartcube.presentation.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.contract.data.repository.ITokenAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenAppRepository: ITokenAppRepository,
) : ViewModel() {
    private val isAuthenticatedFlow: StateFlow<Boolean> = tokenAppRepository.getAccessToken()
        .map { accessToken -> accessToken.isNotEmpty() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            false
        )

    var state by mutableStateOf(
        SplashState()
    )
        private set

    init {
        viewModelScope.launch {
            isAuthenticatedFlow.collect { isAuthenticated ->
                state = state.copy(isAuthenticated = isAuthenticated)
            }
        }
    }

    fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.ToDashboardScreen -> {
                event.callback()
            }

            is SplashEvent.ToLoginScreen -> {
                event.callback()
            }
        }
    }
}