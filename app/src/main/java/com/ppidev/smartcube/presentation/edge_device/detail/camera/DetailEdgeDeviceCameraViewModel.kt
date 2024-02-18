package com.ppidev.smartcube.presentation.edge_device.detail.camera

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IRestartEdgeDeviceUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IStartEdgeDeviceUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IViewEdgeDeviceUseCase
import com.ppidev.smartcube.contract.domain.use_case.notification.IViewNotificationUseCase
import com.ppidev.smartcube.data.remote.dto.toNotificationModel
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailEdgeDeviceViewModel @Inject constructor(
    private val startEdgeDeviceUseCase: Lazy<IStartEdgeDeviceUseCase>,
    private val restartEdgeEdgeDeviceUseCase: Lazy<IRestartEdgeDeviceUseCase>,
    private val viewEdgeDeviceUseCase: Lazy<IViewEdgeDeviceUseCase>,
    private val viewNotificationUseCase: Lazy<IViewNotificationUseCase>
) : ViewModel() {
    var state by mutableStateOf(DetailEdgeDeviceCameraState())
        private set

    fun onEvent(event: DetailEdgeDeviceCameraEvent) {
        viewModelScope.launch {
            when (event) {
                DetailEdgeDeviceCameraEvent.RestartEdgeDeviceCamera -> restartDevice()
                DetailEdgeDeviceCameraEvent.StartEdgeDeviceCamera -> startDevice()
                is DetailEdgeDeviceCameraEvent.GetDetailDeviceCamera -> {
                    getDetailDevice(serverId = event.serverId, deviceId = event.deviceId)
                }

                is DetailEdgeDeviceCameraEvent.SetDeviceInfoCamera -> {
                    state = state.copy(
                        edgeServerId = event.edgeServerId,
                        processId = event.processId
                    )
                }

                DetailEdgeDeviceCameraEvent.HandleCloseDialogMsg -> {
                    state = state.copy(
                        isDialogMsgOpen = false
                    )
                }

                is DetailEdgeDeviceCameraEvent.GetDetailNotificationCamera -> {
                    getDetailNotification(
                        serverId = event.serverId,
                        notificationId = event.notificationId
                    )
                }

                is DetailEdgeDeviceCameraEvent.SetNotificationId -> {
                    state = state.copy(
                        notificationId = event.notificationId
                    )
                }

                is DetailEdgeDeviceCameraEvent.SetOpenImageOverlay -> {
                    state = state.copy(
                        isOpenImageOverlay = event.status
                    )
                }
            }
        }
    }

    private suspend fun startDevice() {
        val edgeDeviceId = state.edgeServerId
        val processId = state.processId

        if (edgeDeviceId == null || processId == null || state.isLoading) {
            return
        }

        startEdgeDeviceUseCase.get().invoke(edgeDeviceId, processId).onEach {
            when (it) {
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        isDialogMsgOpen = true,
                        messageDialog = it.message.toString(),
                        isSuccess = false
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        isDialogMsgOpen = true,
                        messageDialog = it.message,
                        isSuccess = true
                    )
                    Log.d("START_SUCC", it.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun restartDevice() {
        val edgeDeviceId = state.edgeServerId
        val processId = state.processId

        if (edgeDeviceId == null || processId == null || state.isLoading) {
            return
        }

        restartEdgeEdgeDeviceUseCase.get().invoke(edgeDeviceId, processId).onEach {
            when (it) {
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        isDialogMsgOpen = true,
                        messageDialog = it.message,
                        isSuccess = false
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = false,
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        isDialogMsgOpen = true,
                        messageDialog = it.message.toString(),
                        isSuccess = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getDetailDevice(serverId: UInt, deviceId: UInt) {
        viewEdgeDeviceUseCase.get().invoke(edgeDeviceId = deviceId, edgeServerId = serverId)
            .onEach {
                when (it) {
                    is Resource.Error -> {
                        state = state.copy(
                            isLoadingDetailDevice = false
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoadingDetailDevice = true
                        )
                    }

                    is Resource.Success -> {
                        val notifications = it.data?.notifications?.map { item ->
                            item.toNotificationModel()
                        } ?: emptyList()

                        state = state.copy(
                            edgeDeviceDetail = it.data,
                            notifications = notifications,
                            isLoadingDetailDevice = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getDetailNotification(notificationId: UInt, serverId: UInt) {
        viewNotificationUseCase.get()
            .invoke(notificationId = notificationId, edgeServerId = serverId).onEach {
                when (it) {
                    is Resource.Error -> {
                        state = state.copy(
                            isLoadingDetailNotification = false
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoadingDetailNotification = true
                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            notificationDetail = it.data,
                            isLoadingDetailNotification = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}


sealed class DetailEdgeDeviceCameraEvent {
    object StartEdgeDeviceCamera : DetailEdgeDeviceCameraEvent()
    object RestartEdgeDeviceCamera : DetailEdgeDeviceCameraEvent()
    data class GetDetailDeviceCamera(val serverId: UInt, val deviceId: UInt) : DetailEdgeDeviceCameraEvent()
    data class GetDetailNotificationCamera(val serverId: UInt, val notificationId: UInt) :
        DetailEdgeDeviceCameraEvent()

    data class SetNotificationId(val notificationId: UInt) : DetailEdgeDeviceCameraEvent()
    data class SetDeviceInfoCamera(
        val edgeServerId: UInt,
        val processId: Int,
    ) : DetailEdgeDeviceCameraEvent()

    data class SetOpenImageOverlay(val status: Boolean) : DetailEdgeDeviceCameraEvent()
    object HandleCloseDialogMsg : DetailEdgeDeviceCameraEvent()
}