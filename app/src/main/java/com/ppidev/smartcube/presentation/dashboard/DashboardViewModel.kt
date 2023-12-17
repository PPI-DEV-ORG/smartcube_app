package com.ppidev.smartcube.presentation.dashboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import com.ppidev.smartcube.contract.data.repository.ITokenAppRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IEdgeDevicesInfoUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IListEdgeServerUseCase
import com.ppidev.smartcube.contract.domain.use_case.user.IUpdateFcmTokenUseCase
import com.ppidev.smartcube.contract.domain.use_case.user.IUserProfileUseCase
import com.ppidev.smartcube.contract.domain.use_case.weather.IViewCurrentWeather
import com.ppidev.smartcube.data.remote.dto.ServerStatusDto
import com.ppidev.smartcube.domain.model.ServerStatusModel
import com.ppidev.smartcube.utils.CommandMqtt
import com.ppidev.smartcube.utils.convertJsonToDto
import com.ppidev.smartcube.utils.extractCommandAndDataMqtt
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
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
    private val updateFcmTokenUseCase: Lazy<IUpdateFcmTokenUseCase>,
    private val userProfileUserCase: Lazy<IUserProfileUseCase>,
    private val tokenRepository: Lazy<ITokenAppRepository>
) : ViewModel() {
    var state by mutableStateOf(DashboardState())
        private set

    init {
        viewModelScope.launch {
            if (!mqttService.get().checkIfMqttIsConnected()) {
                mqttService.get().connect()
            }
        }
    }

    fun onEvent(event: DashboardEvent) {
        viewModelScope.launch {
            when (event) {
                DashboardEvent.UnsubscribeToMqttService -> unsubscribeFromTopic()
                DashboardEvent.GetCurrentWeather -> getCurrentWeather()
                is DashboardEvent.GetServerInfoMqtt -> getServerInfo(event.topic)

                is DashboardEvent.GetDevicesConfig -> {
                    getEdgeDevicesInfo(event.serverId)
                }

                DashboardEvent.GetListEdgeServer -> getListEdgeServer()
                is DashboardEvent.SetEdgeServerId -> {
                    state = state.copy(
                        edgeServerId = event.id
                    )
                }

                is DashboardEvent.SubscribeTopicMqtt -> {
                    subscribeToTopic(event.topic)
                }

                DashboardEvent.SetToEmptyServerInfo -> {
                    state = state.copy(
                        serverInfoMQTT = null
                    )
                }

                is DashboardEvent.GetProcessDeviceId -> {
                    getProcessDeviceId(event.topic)
                }

                DashboardEvent.StoreFCMToken -> {
                    storeFCMTokenToAPI()
                }

                DashboardEvent.GetUserProfile -> {
                    getUserProfile()
                }
            }
        }
    }

    private fun subscribeToTopic(topic: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!mqttService.get().checkIfMqttIsConnected()) {
                mqttService.get().connect()
            }

            Log.d("MQTT", "subscribed : $topic")

            mqttService.get().subscribeToTopic(topic) { _, msg ->
                val (command, data) = extractCommandAndDataMqtt(msg)
                Log.d("MQTT", "command : $command")
                when (command) {
                    CommandMqtt.GET_SERVER_INFO -> {
                        val dataDecoded = convertJsonToDto<ServerStatusDto>(data)
                        Log.d("MQTT", dataDecoded.toString())
                        if (dataDecoded != null) {
                            updateServerInfoMqtt(serverInfo = dataDecoded)
                        }
                    }

                    CommandMqtt.GET_PROCESS_DEVICE_INDEX -> {
                        Log.d("MQTT", data.toString())

                    }
                }
            }
        }
    }

    private suspend fun getUserProfile() {
        userProfileUserCase.get().invoke().onEach {
            when(it) {
                is Resource.Error -> {
                    state = state.copy(
                        loading = DashboardState.Loading(
                            isLoadingUserProfile = false
                        )
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(
                        loading = DashboardState.Loading(
                            isLoadingUserProfile = true
                        )
                    )
                }
                is Resource.Success -> {
                    val data = it.data?.data

                    state = state.copy(
                        username = data?.userName.toString(),
                        email = data?.userEmail.toString(),
                        loading = DashboardState.Loading(
                            isLoadingUserProfile = false
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getListEdgeServer() {
        listEdgeServerUseCase.get().invoke().onEach {
            when (it) {
                is Resource.Error -> {
                    state = state.copy(
                        error = DashboardState.Error(
                            listServerError = it.message.toString(),
                            listServerCode = it.statusCode
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
                    val res = it.data?.data
                    val servers: List<String> = res?.map { server -> server.name } ?: emptyList()
                    val serverIds: List<UInt> = res?.map { server -> server.id } ?: emptyList()
                    state = state.copy(
                        listServer = servers,
                        edgeServerId = res?.firstOrNull()?.id,
                        listServerId = serverIds,
                        loading = DashboardState.Loading(
                            isLoadingListServer = false
                        ),
                        error = DashboardState.Error(
                            listServerError = ""
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
                        ),
                        error = DashboardState.Error(
                            listDevicesError = it.message.toString()
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

                    state = state.copy(
                        listDevices = res?.devices ?: emptyList(),
                        mqttPublishTopic = res?.mqttPubTopic,
                        mqttSubscribeTopic = res?.mqttSubTopic,
                        loading = DashboardState.Loading(
                            isLoadingListDevices = false
                        ),
                        error = DashboardState.Error(
                            listDevicesError = ""
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

    private fun getServerInfo(topic: String) {
        viewModelScope.launch {
            Log.d("MQTT", "publish : $topic")
            mqttService.get()
                .publishToTopic(topic, CommandMqtt.GET_SERVER_INFO)
        }
    }

    private fun getProcessDeviceId(topic: String) {
        viewModelScope.launch {
            Log.d("MQTT", "publish $topic")

            mqttService.get().publishToTopic(topic, CommandMqtt.GET_PROCESS_DEVICE_INDEX)
        }
    }


    private suspend fun storeFCMTokenToAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            val fcmToken = tokenRepository.get().getFcmToken().first()

            updateFcmTokenUseCase.get().invoke(fcmToken).onEach {
                when (it) {
                    is Resource.Error -> {
                        Log.d("FCM", "Failed update token")
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Log.d("FCM", "success update token")
                    }
                }
            }.launchIn(viewModelScope)
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
                    upTime = serverInfo.upTime,
                    memoryTotal = serverInfo.memoryTotal
                )
            )
        }
    }
}