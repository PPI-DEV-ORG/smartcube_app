package com.ppidev.smartcube.presentation.notification.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.contract.domain.use_case.notification.IViewNotificationUseCase
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationDetailViewModel @Inject constructor(
    private val viewNotificationUseCase: Lazy<IViewNotificationUseCase>
) : ViewModel() {
    var state by mutableStateOf(NotificationDetailState())
        private set

    fun onEvent(event: NotificationDetailEvent) {
        viewModelScope.launch {
            when (event) {
                is NotificationDetailEvent.GetDetailNotification -> getDetailNotification(
                    event.notificationId,
                    event.edgeServerId
                )

                is NotificationDetailEvent.SetOverlayImageStatus -> {
                    state = state.copy(
                        isOpenImageOverlay = event.status
                    )
                }
            }
        }
    }

    private fun getDetailNotification(notificationId: UInt, edgeServerId: UInt) {
        viewNotificationUseCase.get().invoke(notificationId, edgeServerId).onEach {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    state = state.copy(
                        notificationModel = it.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}

sealed class NotificationDetailEvent {
    data class GetDetailNotification(val notificationId: UInt, val edgeServerId: UInt) :
        NotificationDetailEvent()

    data class SetOverlayImageStatus(val status: Boolean) : NotificationDetailEvent()
}