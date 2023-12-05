package com.ppidev.smartcube.presentation.edge_device.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IEdgeDevicesInfoUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IListEdgeServerUseCase
import com.ppidev.smartcube.utils.CommandMqtt
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEdgeDeviceViewModel @Inject constructor(
    private val edgeDeviceInfoUse: Lazy<IEdgeDevicesInfoUseCase>,
    private val listEdgeServerUseCase: Lazy<IListEdgeServerUseCase>,
    private val mqttService: IMqttService
) : ViewModel() {
    var state by mutableStateOf(ListEdgeDeviceState())
        private set


    fun onEvent(event: ListEdgeDeviceEvent) {
        viewModelScope.launch {
            when (event) {
                is ListEdgeDeviceEvent.GetListEdgeDevice -> {
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

                ListEdgeDeviceEvent.ListenMqttClient -> {
                    Log.d("MQTT", "subscribe to : ${state.edgeDevicesInfo?.mqttSubTopic}")

                    listenMqtt(
                        state.edgeDevicesInfo?.mqttSubTopic,
                        state.edgeDevicesInfo?.mqttPubTopic
                    )
                }

                ListEdgeDeviceEvent.DisconnectMqtt -> {
                    mqttService.disconnect()
                    Log.d("MQTT", "disconnect")
                }

                ListEdgeDeviceEvent.UnsubscribeFromTopicMqtt -> {
                    unsubscribeTopicMqtt()
                    Log.d("MQTT", "unsubscribe from ${state.edgeDevicesInfo?.mqttSubTopic}")
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
                        edgeDevicesInfo = it.data?.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getListEdgeServer() {
        listEdgeServerUseCase.get().invoke().onEach {
            when (it) {
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

    private fun listenMqtt(subTopic: String?, pushTopic: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (subTopic == null || pushTopic == null) {
                return@launch
            }

            mqttService.connect()

            mqttService.subscribeToTopic(topic = subTopic) { topic, msg ->
                Log.d("MQTT", "topic = $topic | Msg = $msg")
            }

            mqttService.publishToTopic(pushTopic, CommandMqtt.GET_PROCESS_DEVICE_INDEX)
        }
    }

    private fun unsubscribeTopicMqtt() {
        viewModelScope.launch {
            val topic = state.edgeDevicesInfo?.mqttSubTopic ?: return@launch
            mqttService.unsubscribeFromTopic(topic)
        }
    }
}