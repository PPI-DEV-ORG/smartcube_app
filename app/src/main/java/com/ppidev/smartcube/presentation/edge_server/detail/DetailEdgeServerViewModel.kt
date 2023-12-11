package com.ppidev.smartcube.presentation.edge_server.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IEdgeDevicesInfoUseCase
import com.ppidev.smartcube.data.remote.dto.ServerStatusDto
import com.ppidev.smartcube.utils.CommandMqtt
import com.ppidev.smartcube.utils.convertJsonToDto
import com.ppidev.smartcube.utils.extractCommandAndDataMqtt
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailEdgeServerViewModel @Inject constructor(
    private val edgeDeviceInfoUse: Lazy<IEdgeDevicesInfoUseCase>,
    private val mqttService: IMqttService
) : ViewModel() {

    var state by mutableStateOf(DetailEdgeServerState())
        private set

    fun onEvent(event: DetailEdgeServerEvent) {
        viewModelScope.launch {
            when (event) {
                is DetailEdgeServerEvent.GetDetailDevicesInfo -> {
                    getEdgeDevicesInfo(event.edgeServerId)
                }

                is DetailEdgeServerEvent.UnSubscribeFromMqttTopic -> {
                    mqttService
                        .unsubscribeFromTopic(event.topic)
                }

                is DetailEdgeServerEvent.SetDialogStatus -> {
                    state = state.copy(
                        isDialogOpen = event.status
                    )
                }

                is DetailEdgeServerEvent.GetServerInfoMqtt -> {
                    getServerInfoMqtt(event.topic)
                }

                is DetailEdgeServerEvent.SubscribeToTopicMqtt -> {
                    subscribeToTopic(event.topic)
                }
            }
        }
    }

    private suspend fun getEdgeDevicesInfo(edgeServerId: UInt) {
        edgeDeviceInfoUse.get().invoke(edgeServerId).onEach {
            when (it) {
                is Resource.Error -> {
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
                    state = state.copy(
                        isLoading = false,
                        edgeDevicesInfo = it.data?.data,
                        devices = it.data?.data?.devices ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun subscribeToTopic(topic: String) {
        viewModelScope.launch {
            if (!mqttService.checkIfMqttIsConnected()) {
                mqttService.connect()
            }

            mqttService.subscribeToTopic(topic) { _, msg ->
                val (command, data) = extractCommandAndDataMqtt(msg)

                when (command) {
                    CommandMqtt.GET_SERVER_INFO -> {
                        val dataDecoded = convertJsonToDto<ServerStatusDto>(data)
                        if (dataDecoded != null) {
                            updateServerInfo(dataDecoded)
                        }
                    }
                }
            }
        }
    }

    private fun getServerInfoMqtt(topic: String) {
        viewModelScope.launch {
            mqttService.publishToTopic(
                topic,
                CommandMqtt.GET_SERVER_INFO
            )
        }
    }

    private fun updateServerInfo(serverInfo: ServerStatusDto) {
        viewModelScope.launch {
            state = state.copy(
                serverInfo = serverInfo
            )
        }
    }
}

