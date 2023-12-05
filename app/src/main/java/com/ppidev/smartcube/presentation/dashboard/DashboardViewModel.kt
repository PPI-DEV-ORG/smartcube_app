package com.ppidev.smartcube.presentation.dashboard

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
import com.ppidev.smartcube.contract.domain.use_case.weather.IViewCurrentWeather
import com.ppidev.smartcube.data.remote.dto.EdgeDevice
import com.ppidev.smartcube.data.remote.dto.ServerStatusDto
import com.ppidev.smartcube.domain.model.ServerStatusModel
import com.ppidev.smartcube.utils.CommandMqtt
import com.ppidev.smartcube.utils.convertJsonToDto
import com.ppidev.smartcube.utils.extractCommandAndDataMqtt
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val viewCurrentWeather: Lazy<IViewCurrentWeather>,
    private val mqttService: Lazy<IMqttService>,
    private val listEdgeServerUseCase: Lazy<IListEdgeServerUseCase>,
    private val edgeDevicesInfo: Lazy<IEdgeDevicesInfoUseCase>,
) : ViewModel() {
    var state by mutableStateOf(DashboardState())
        private set

    init {
        listenReceiveMessageMqtt()
    }

    fun onEvent(event: DashboardEvent) {
        viewModelScope.launch {
            when (event) {
                DashboardEvent.GetServerInfo -> getServerInfo()
                DashboardEvent.UnsubscribeToMqttService -> unsubscribeFromTopic()
                DashboardEvent.GetCurrentWeather -> getCurrentWeather()

                is DashboardEvent.GetDevicesConfig -> {
                    getEdgeDevicesInfo(event.serverId)
                }

                DashboardEvent.GetListEdgeServer -> getListEdgeServer()
                is DashboardEvent.SetEdgeServerId -> {
                    state = state.copy(
                        edgeServerId = event.id
                    )
                }
            }
        }
    }

    private fun listenReceiveMessageMqtt() {
        viewModelScope.launch(Dispatchers.IO) {
            mqttService.get().connect()
            val mqttPublishTopic = state.mqttPublishTopic
            val mqttSubscribeTopic = state.mqttSubscribeTopic


            if (mqttPublishTopic != null && mqttSubscribeTopic != null) {
                mqttService.get().subscribeToTopic(mqttSubscribeTopic) { _, msg ->
                    val (command, data) = extractCommandAndDataMqtt(msg)

                    when (command) {
                        CommandMqtt.GET_SERVER_INFO -> {
                            val dataDecoded = convertJsonToDto<ServerStatusDto>(data)
                            dataDecoded?.let {
                                updateServerInfoMqtt(serverInfo = it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getListEdgeServer() {
        listEdgeServerUseCase.get().invoke().onEach {
            when (it) {
                is Resource.Error -> {
                    state = state.copy(
                        error = DashboardState.Error(
                            listServerError = it.message.toString()
                        ),
                        loading = DashboardState.Loading(
                            isLoadingListServer = false
                        )
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        loading = DashboardState.Loading(
                            isLoadingListServer = true
                        )
                    )
                }

                is Resource.Success -> {
                    Log.d("LIST_SERVER", "success, ${it.data?.message}")
                    val res = it.data?.data
                    var servers: List<String> = emptyList()
                    var serverIds: List<UInt> = emptyList()

                    if (res != null) {
                        servers = res.map { item ->
                            item.name
                        } as List<String>

                        serverIds = res.map { item ->
                            item.id
                        }
                    }

                    state = state.copy(
                        listServer = servers,
                        edgeServerId = res?.get(0)?.id,
                        listServerId = serverIds,
                        loading = DashboardState.Loading(
                            isLoadingListServer = false
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getEdgeDevicesInfo(edgeServerId: UInt) {
        edgeDevicesInfo.get().invoke(edgeServerId).onEach {
            when (it) {
                is Resource.Error -> {
                    state = state.copy(
                        loading = DashboardState.Loading(
                            isLoadingListDevices = false
                        )
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        loading = DashboardState.Loading(
                            isLoadingListDevices = true
                        )
                    )
                }

                is Resource.Success -> {
                    val res = it.data?.data
                    var listDevices: List<EdgeDevice> = emptyList()

                    if (res != null) {
                        listDevices = res.devices
                    }

                    state = state.copy(
                        listDevices = listDevices,
                        mqttPublishTopic = res?.mqttPubTopic,
                        mqttSubscribeTopic = res?.mqttSubTopic,
                        loading = DashboardState.Loading(
                            isLoadingListDevices = false
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun getCurrentWeather() {
        viewCurrentWeather.get().invoke().onEach {
            when (it) {
                is Resource.Error -> {
                }

                is Resource.Loading -> {
                }

                is Resource.Success -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getServerInfo() {
        viewModelScope.launch {
            val mqttPublishTopic = state.mqttPublishTopic ?: return@launch

            mqttService.get()
                .publishToTopic(mqttPublishTopic, CommandMqtt.GET_SERVER_INFO)
        }
    }


    private fun unsubscribeFromTopic() {
        viewModelScope.launch {
            val mqttSubscribeTopic = state.mqttSubscribeTopic ?: return@launch

            mqttService.get().unsubscribeFromTopic(mqttSubscribeTopic)
        }
    }

    private fun updateServerInfoMqtt(serverInfo: ServerStatusDto) {
        viewModelScope.launch {
            state = state.copy(
                serverInfoMQTT = ServerStatusModel(
                    memoryFree = serverInfo.memoryFree,
                    cpuTemp = serverInfo.cpuTemp,
                    storageFree = serverInfo.storage.freeSpace,
                    fanSpeed = serverInfo.fanSpeed,
                    upTime = serverInfo.upTime
                )
            )
        }
    }
}