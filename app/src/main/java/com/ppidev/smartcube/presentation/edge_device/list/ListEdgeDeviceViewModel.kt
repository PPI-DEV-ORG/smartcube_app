package com.ppidev.smartcube.presentation.edge_device.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IEdgeDevicesInfoUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IListEdgeServerUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEdgeDeviceViewModel @Inject constructor(
    private val edgeDeviceInfoUse: Lazy<IEdgeDevicesInfoUseCase>,
    private val listEdgeServerUseCase: Lazy<IListEdgeServerUseCase>
) : ViewModel() {
    var state by mutableStateOf(ListEdgeDeviceState())
        private set


    fun onEvent(event: ListEdgeDeviceEvent) {
        viewModelScope.launch {
            when(event) {
                is ListEdgeDeviceEvent.GetLstEdgeDevice -> {
                    getEdgeDevicesInfo(event.edgeServerId)
                }

                ListEdgeDeviceEvent.GetLstEdgeServer -> {
                    getListEdgeServer()
                }

                is ListEdgeDeviceEvent.SetEdgeServerId -> {
                    state = state.copy(
                        serverId = event.edgeServerId
                    )
                }
            }
        }
    }

    private suspend fun getEdgeDevicesInfo(edgeServerId: UInt) {
        edgeDeviceInfoUse.get().invoke(edgeServerId).onEach {
            when(it) {
                is Resource.Error -> {
                    Log.d("RES_ER", it.data?.data.toString())

                    state = state.copy(
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    Log.d("RES", it.data?.data.toString())
                    state = state.copy(
                        isLoading = false,
                        edgeDevicesInfo = it.data?.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getListEdgeServer() {
        listEdgeServerUseCase.get().invoke().onEach {
            when(it) {
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if (it.data?.data != null && it.data.data.isNotEmpty()) {
                        state = state.copy(
                            listEdgeServer = it.data.data,
                            serverId = it.data.data[0].id
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}