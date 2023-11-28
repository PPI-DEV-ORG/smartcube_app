package com.ppidev.smartcube.presentation.edge_device.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IRestartEdgeDeviceUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IStartEdgeDeviceUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailEdgeDeviceViewModel @Inject constructor(
    private val startEdgeDeviceUseCase: Lazy<IStartEdgeDeviceUseCase>,
    private val restartEdgeEdgeDeviceUseCase: Lazy<IRestartEdgeDeviceUseCase>
) : ViewModel() {
    var state by mutableStateOf(DetailEdgeDeviceState())
        private set

    fun onEvent(event: DetailEdgeDeviceEvent) {
        viewModelScope.launch {
            when (event) {
                DetailEdgeDeviceEvent.GetDetailDevice -> {}
                DetailEdgeDeviceEvent.RestartEdgeDevice -> restartDevice()
                DetailEdgeDeviceEvent.StartEdgeDevice -> startDevice()
                is DetailEdgeDeviceEvent.SetDeviceInfo -> {
                    state = state.copy(
                        edgeServerId = event.edgeServerId,
                        processId = event.processId
                    )
                }
                DetailEdgeDeviceEvent.HandleCloseDialogMsg -> {
                    state = state.copy(
                        isDialogMsgOpen = false
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
                    Log.d("START_ERR", it.message.toString())
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
                        messageDialog = it.data?.message.toString(),
                        isSuccess = true
                    )
                    Log.d("START_SUCC", it.data?.message.toString())
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
                        messageDialog = it.message.toString(),
                        isSuccess = false
                    )
                    Log.d("RESTART_ERR", it.message.toString())
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
                        messageDialog = it.data?.message.toString(),
                        isSuccess = true
                    )
                    Log.d("RESTART_SUCC", it.data?.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}