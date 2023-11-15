package com.ppidev.smartcube.presentation.splash

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.contract.data.repository.ITokenAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenAppRepository: ITokenAppRepository,
) : ViewModel() {
    private val resultChannel = Channel<Boolean>()
    private val authResults = resultChannel.receiveAsFlow()

    var state by mutableStateOf(
        SplashState(
            isAuth = authResults
        )
    )
        private set

    init {
        authenticate()
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

    private fun authenticate() {
        viewModelScope.launch {
            tokenAppRepository.getAccessToken().collect {
                if (it.isEmpty()) {
                    resultChannel.send(false)
                } else {
                    resultChannel.send(true)
                }
            }
        }
    }
}