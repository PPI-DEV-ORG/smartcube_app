package com.ppidev.smartcube.presentation.notification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.domain.use_case.notification.IListNotificationsUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val listNotificationsUseCase: Lazy<IListNotificationsUseCase>
): ViewModel() {
    var state by mutableStateOf(NotificationState())
        private set

    fun onEvent(event: NotificationEvent) {
        when(event){
            NotificationEvent.GetListNotification -> getListNotification()
            is NotificationEvent.ToNotificationDetail -> event.callback
        }
    }

    private fun getListNotification() {
        listNotificationsUseCase.get().invoke().onEach {
            when(it) {
                is Resource.Loading -> {
                    setStatusLoading(true)
                }
                is Resource.Success -> {
                    state = state.copy(
                        notifications = it.data ?: emptyList()
                    )
                    setStatusLoading(false)
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = it.message ?: ""
                    )
                    setStatusLoading(false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun setStatusLoading(status: Boolean) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = status
            )
        }
    }
}