package com.ppidev.smartcube.presentation.edge_server.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IEdgeDevicesInfoUseCase
import com.ppidev.smartcube.data.remote.dto.DeviceConfigDto
import com.ppidev.smartcube.data.remote.dto.DeviceStatusDto
import com.ppidev.smartcube.domain.model.DeviceConfigModel
import com.ppidev.smartcube.presentation.dashboard.model.CommandMqtt
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class DetailEdgeServerViewModel @Inject constructor(
    private val edgeDeviceInfoUse: Lazy<IEdgeDevicesInfoUseCase>,
    private val mqttService: IMqttService
) : ViewModel() {

    var state by mutableStateOf(DetailEdgeServerState())
        private set

    private val gson = Gson()

    fun onEvent(event: DetailEdgeServerEvent) {
        viewModelScope.launch {
            when (event) {
                is DetailEdgeServerEvent.GetDetailDevicesInfo -> {
                    getEdgeDevicesInfo(event.edgeServerId)
                }

                DetailEdgeServerEvent.ListenMqtt -> {
                    listenMqttClient()
                }

                DetailEdgeServerEvent.UnListenMqtt -> {
                    val mqttSubTopic = state.edgeDevicesInfo?.mqttSubTopic

                    if (mqttSubTopic != null) {
                        mqttService
                            .unsubscribeFromTopic(mqttSubTopic)
                    }

                    mqttService.disconnect()
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

    private fun listenMqttClient() {
        viewModelScope.launch(Dispatchers.IO) {
            val mqttSubTopic = state.edgeDevicesInfo?.mqttSubTopic
            val mqttPubTopic = state.edgeDevicesInfo?.mqttPubTopic

            if (mqttSubTopic != null && mqttPubTopic != null) {
                mqttService.connect()

                mqttService.subscribeToTopic(mqttSubTopic) { _, msg ->
                    val json = String(msg.payload)
                    val result = gson.fromJson<Map<String, Any>>(
                        json,
                        object : TypeToken<Map<String, Any>>() {}.type
                    )
                    val command = Json.decodeFromString<String>(gson.toJson(result["command"]))
                    val data = result["data"]
                    Log.d("DATA", data.toString())

                    when (command) {
                        CommandMqtt.GET_SERVER_INFO -> {
                            val dataDecoded = Json.decodeFromString<DeviceStatusDto>(
                                gson.toJson(data)
                            )
                            updateServerInfo(dataDecoded)
                        }

                        CommandMqtt.GET_DEVICES_CONFIG -> {
                            val dataDecoded = Json.decodeFromString<List<DeviceConfigDto>>(
                                gson.toJson(data)
                            )
                            updateListDevices(dataDecoded)
                        }
                    }
                }

                publishTopic(mqttPubTopic)
            }
        }
    }

    private fun updateServerInfo(serverInfo: DeviceStatusDto) {
        viewModelScope.launch {
            state = state.copy(
                serverInfo = serverInfo
            )
        }
    }

    private fun updateListDevices(devices: List<DeviceConfigDto>) {
        viewModelScope.launch {
            state = state.copy(
                devices = devices
            )
        }
    }

    private fun publishTopic(mqttPubTopic: String) {
        viewModelScope.launch {
            mqttService.publishToTopic(
                mqttPubTopic,
                CommandMqtt.GET_DEVICES_CONFIG
            )
            mqttService.publishToTopic(
                mqttPubTopic,
                CommandMqtt.GET_SERVER_INFO
            )
        }
    }
}

