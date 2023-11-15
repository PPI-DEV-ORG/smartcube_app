package com.ppidev.smartcube.presentation.dashboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ppidev.smartcube.BuildConfig
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import com.ppidev.smartcube.contract.domain.use_case.notification.IListNotificationUseCase
import com.ppidev.smartcube.contract.domain.use_case.weather.IViewCurrentWeather
import com.ppidev.smartcube.data.remote.dto.DeviceConfigDto
import com.ppidev.smartcube.data.remote.dto.DeviceStatusDto
import com.ppidev.smartcube.data.remote.dto.MLModelDto
import com.ppidev.smartcube.data.remote.dto.toDeviceConfigModel
import com.ppidev.smartcube.data.remote.dto.toMLModel
import com.ppidev.smartcube.domain.model.DeviceStatusModel
import com.ppidev.smartcube.domain.model.NotificationModel
import com.ppidev.smartcube.presentation.dashboard.model.CommandMqtt
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getNotificationsUseCase: Lazy<IListNotificationUseCase>,
    private val mqttService: Lazy<IMqttService>,
    private val viewCurrentWeather: Lazy<IViewCurrentWeather>
) : ViewModel() {

    private val gson = Gson()

    data class HostDevice(
        val id: Int,
        val name: String,
        val description: String,
        val topic1: String,
        val topic2: String
    )

    var state by mutableStateOf(DashboardState())
        private set

    init {
        viewModelScope.launch {
            listenReceiveMessageMqtt()
        }
    }

    fun onEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.GetListNotification -> getNotifications()
            DashboardEvent.GetServerSummary -> getServerSummary()
            DashboardEvent.SubscribeToMqttService -> subscribeToMqttClient()
            DashboardEvent.GetDeviceConfig -> getListCameras()
            DashboardEvent.GetListModelInstalled -> getListModelsInstalled()
            DashboardEvent.UnsubscribeToMqttService -> unsubscribeFromTopic()
            DashboardEvent.GetCurrentWeather -> getCurrentWeather()
            DashboardEvent.CloseBottomSheet -> onCloseBottomSheet()
            DashboardEvent.OpenBottomSheet -> onOpenBottomSheet()
            is DashboardEvent.OnSelectServer -> onSelectServer(
                event.index,
                callback = { event.callback })
        }
    }

    private fun onCloseBottomSheet() {
        viewModelScope.launch {
            state = state.copy(
                openBottomSheet = false
            )
        }
    }

    private fun onOpenBottomSheet() {
        viewModelScope.launch {
            state = state.copy(
                openBottomSheet = true
            )
        }
    }

    private fun onSelectServer(index: Int, callback: () -> Unit) {
        viewModelScope.launch {
            state = state.copy(
                serverSelected = index
            )

            callback()
        }
    }

    private fun getNotifications() {
        getNotificationsUseCase.get().invoke().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    updateNotifications(result.data)
                    state = state.copy(
                        isLoadingNotification = false
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoadingNotification = false
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        isLoadingNotification = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun subscribeToMqttClient() {
        viewModelScope.launch {
            mqttService.get().subscribeToTopic(BuildConfig.MQTT_TOPIC2)
        }
    }

    private fun getServerSummary() {
        viewModelScope.launch {
            mqttService.get().publishToTopic(BuildConfig.MQTT_TOPIC, CommandMqtt.GET_SERVER_INFO)
        }
    }

    private fun getListModelsInstalled() {
        viewModelScope.launch {
            mqttService.get()
                .publishToTopic(BuildConfig.MQTT_TOPIC, CommandMqtt.GET_INSTALLED_MODEL)
        }
    }

    private fun getListCameras() {
        viewModelScope.launch {
            mqttService.get().publishToTopic(BuildConfig.MQTT_TOPIC, CommandMqtt.GET_DEVICES_CONFIG)
        }
    }

    private fun listenReceiveMessageMqtt() {
        mqttService.get().listenSubscribedTopic { mqttMessage ->
            val json = String(mqttMessage.payloadAsBytes)
            val result = gson.fromJson<Map<String, Any>>(
                json,
                object : TypeToken<Map<String, Any>>() {}.type
            )

            if (result.containsKey("hostDeviceStatus")) {
                val hostDeviceStatusResponse = result["hostDeviceStatus"]
                val hostDeviceStatus =
                    Json.decodeFromString<DeviceStatusDto>(gson.toJson(hostDeviceStatusResponse))
                updateServerSummary(hostDeviceStatus)
            }

            if (result.containsKey("getInstalledModels")) {
                val installedModelsResponse = result["getInstalledModels"]
                val listInstalledModels =
                    Json.decodeFromString<List<MLModelDto>>(gson.toJson(installedModelsResponse))
                updateListMLModel(listInstalledModels)
            }

            if (result.containsKey("getDeviceConfig")) {
                val devicesConfigResponse = result["getDeviceConfig"]
                val listDevicesConfig =
                    Json.decodeFromString<List<DeviceConfigDto>>(gson.toJson(devicesConfigResponse))
                updateListDeviceConfig(listDevicesConfig)
            }

        }
    }

    private fun unsubscribeFromTopic() {
        viewModelScope.launch {
            mqttService.get().unsubscribeFromTopic(BuildConfig.MQTT_TOPIC2)
        }
    }

    private fun updateServerSummary(newServerSummary: DeviceStatusDto) {
        viewModelScope.launch {
            state = state.copy(
                serverSummary = DeviceStatusModel(
                    memoryFree = newServerSummary.memoryFree,
                    cpuTemp = newServerSummary.cpuTemp,
                    storageFree = newServerSummary.storage.freeSpace,
                    fanSpeed = newServerSummary.fanSpeed,
                    upTime = newServerSummary.upTime
                )
            )
        }
    }

    private fun updateListMLModel(listModelsML: List<MLModelDto>) {
        viewModelScope.launch {
            state = state.copy(
                listModelsML = listModelsML.map {
                    it.toMLModel()
                }
            )
        }
    }

    private fun updateListDeviceConfig(listDeviceConfig: List<DeviceConfigDto>) {
        viewModelScope.launch {
            state = state.copy(
                listDevicesConfig = listDeviceConfig.map {
                    it.toDeviceConfigModel()
                }
            )
        }
    }

    private fun getCurrentWeather() {
        viewCurrentWeather.get().invoke().onEach {
            when (it) {
                is Resource.Error -> {
                    Log.d("WEATHER_ERR", it.data.toString())

                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    Log.d("WEATHER", it.data?.data.toString())
                    state = state.copy(
                        weather = it.data?.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateNotifications(listNotifications: List<NotificationModel>?) {
        viewModelScope.launch {
            state = state.copy(
                notifications = listNotifications ?: emptyList()
            )
        }
    }
}