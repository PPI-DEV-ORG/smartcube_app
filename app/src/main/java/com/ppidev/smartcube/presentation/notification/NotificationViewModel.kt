package com.ppidev.smartcube.presentation.notification

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.domain.use_case.notification.IListNotificationUseCase
import com.ppidev.smartcube.contract.domain.use_case.notification.IViewNotificationUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val listNotificationsUseCase: Lazy<IListNotificationUseCase>,
    private val detailNotificationUseCase: Lazy<IViewNotificationUseCase>
) : ViewModel() {
    var state by mutableStateOf(NotificationState())
        private set

    fun onEvent(event: NotificationEvent) {
        viewModelScope.launch {
            when (event) {
                NotificationEvent.GetListNotification -> getListNotification()
                is NotificationEvent.GetNotificationDetail -> getDetailNotification(
                    event.notificationId,
                    event.edgeServerId
                )

                is NotificationEvent.SetNotificationId -> {
                    state = state.copy(
                        notificationId = event.notificationId
                    )
                }

                is NotificationEvent.SetOpenImageOverlay -> {
                    state = state.copy(
                        isOpenImageOverlay = event.status
                    )
                }

                NotificationEvent.OnRefresh -> {
                    getListNotification()
                }
            }
        }
    }

    private fun getListNotification() {
        listNotificationsUseCase.get().invoke().onEach {
            when (it) {
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

    private fun getDetailNotification(notificationId: UInt, edgeServerId: UInt) {
        detailNotificationUseCase.get().invoke(notificationId, edgeServerId).onEach {
            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Log.d("TEST", it.data?.data.toString())
                    state = state.copy(
                        detailNotification = it.data?.data
                    )
                }

                is Resource.Error -> {
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