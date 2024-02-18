package com.ppidev.smartcube.presentation.edge_device.detail.sensor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IReadEdgeDeviceSensorUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IViewEdgeDeviceUseCase
import com.ppidev.smartcube.contract.domain.use_case.notification.IViewNotificationUseCase
import com.ppidev.smartcube.utils.FilterChart
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.utils.isoDateToEpoch
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DetailEdgeDeviceSensorViewModel @Inject constructor(
    private val readEdgeDeviceSensor: Lazy<IReadEdgeDeviceSensorUseCase>,
    private val viewEdgeDeviceUseCase: Lazy<IViewEdgeDeviceUseCase>,
    private val viewNotificationUseCase: Lazy<IViewNotificationUseCase>
) : ViewModel() {
    var state by mutableStateOf(DetailEdgeDeviceSensorState())
        private set

    fun onEvent(event: DetailEdgeDeviceSensorEvent) {
        viewModelScope.launch {
            when (event) {
                is DetailEdgeDeviceSensorEvent.GetDataNotificationsByDevice -> {
                    getNotificationsDevice(event.deviceId, event.serverId)
                }

                is DetailEdgeDeviceSensorEvent.GetDetailDevice -> {
                    getDetailDevice(event.deviceId, event.serverId)
                }

                is DetailEdgeDeviceSensorEvent.GetSensorData -> {
                    getDeviceSensorData(
                        event.deviceId,
                        event.serverId,
                        event.startDate,
                        event.endDate
                    )
                }

                is DetailEdgeDeviceSensorEvent.SetNotificationId -> {
                    state = state.copy(
                        notificationId = event.notificationId
                    )
                }

                is DetailEdgeDeviceSensorEvent.GetNotificationDetail -> {
                    getDetailNotification(event.edgeServerId, event.notificationId)
                }

                is DetailEdgeDeviceSensorEvent.SetFilterChart -> {
                    when (val filter = event.filter) {
                        FilterChart.OneDay -> {
                            state = state.copy(
                                filterChart = filter,
                                startDate = SimpleDateFormat(
                                    "yyyy-MM-dd",
                                    Locale.getDefault()
                                ).format(Date())
                            )
                        }

                        FilterChart.OneWeek -> {
                            val currentDate = Date()
                            val calendar = Calendar.getInstance()
                            calendar.time = currentDate
                            calendar.add(Calendar.WEEK_OF_YEAR, -1)
                            val startDate = SimpleDateFormat(
                                "yyyy-MM-dd",
                                Locale.getDefault()
                            ).format(calendar.time)

                            state = state.copy(
                                filterChart = filter,
                                startDate = startDate
                            )

                        }
                    }
                }
            }
        }
    }

    private suspend fun getDetailDevice(edgeDeviceId: UInt, edgeServerId: UInt) {
        viewEdgeDeviceUseCase.get().invoke(edgeServerId, edgeDeviceId).onEach {
            when (it) {
                is Resource.Error -> {
                    state = state.copy(
                        isLoadingDeviceDetail = false
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        isLoadingDeviceDetail = true
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        edgeDeviceDetail = it.data,
                        isLoadingDeviceDetail = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getNotificationsDevice(
        edgeDeviceId: UInt,
        edgeServerId: UInt,
    ) {

    }

    private fun getDetailNotification(edgeServerId: UInt, notificationId: UInt) {
        viewNotificationUseCase.get()
            .invoke(edgeServerId = edgeServerId, notificationId = notificationId).onEach {
                when (it) {
                    is Resource.Error -> {
                        state = state.copy(
                            isLoadingNotificationDetail = false
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoadingNotificationDetail = true
                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            detailNotification = it.data,
                            isLoadingNotificationDetail = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun getDeviceSensorData(
        edgeDeviceId: UInt,
        edgeServerId: UInt,
        startDate: String,
        endDate: String
    ) {
        readEdgeDeviceSensor.get().invoke(
            edgeDeviceId = edgeDeviceId,
            edgeServerId = edgeServerId,
            startDate = startDate,
            endDate = endDate
        ).onEach {
            when (it) {
                is Resource.Error -> {
                    state = state.copy(
                        isLoadingSensorData = false
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        isLoadingSensorData = true
                    )
                }

                is Resource.Success -> {
                    val listTemperature: MutableList<Pair<Long, Float>> = mutableListOf()
                    val listHumidity: MutableList<Pair<Long, Float>> = mutableListOf()
                    val listGas: MutableList<Pair<Long, Float>> = mutableListOf()

                    val data = it.data

                    if (!data.isNullOrEmpty()) {
                        for ((_, item) in data.withIndex()) {
                            val capturedAt = item.capturedAt
                            val epochCapturedAt = isoDateToEpoch(capturedAt) ?: 0

                            for (measurement in item.dataMeasured) {
                                val sensorType = measurement.sensorType
                                val dataValue = measurement.data

                                val measurementObj = Pair(epochCapturedAt, dataValue.toFloat())

                                when (sensorType) {
                                    "temperature" -> {
                                        listTemperature.add(measurementObj)
                                    }

                                    "humidity" -> {
                                        listHumidity.add(measurementObj)
                                    }

                                    "gas" -> {
                                        listGas.add(measurementObj)
                                    }
                                }
                            }
                        }

                        state = state.copy(
                            listTemperatureData = listTemperature,
                            listHumidityData = listHumidity,
                            listSmokeData = listGas,
                            temperatureUnitMeasurement = data[0].dataMeasured[0].unitMeasure,
                            humidityUnitMeasurement = data[0].dataMeasured[1].unitMeasure,
                            gasUnitMeasurement = data[0].dataMeasured[2].unitMeasure,
                            isLoadingSensorData = false
                        )
                    } else {
                        state = state.copy(
                            listTemperatureData = listTemperature,
                            listHumidityData = listHumidity,
                            listSmokeData = listGas,
                            isLoadingSensorData = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}

sealed class DetailEdgeDeviceSensorEvent {
    data class GetDataNotificationsByDevice(val serverId: UInt, val deviceId: UInt) :
        DetailEdgeDeviceSensorEvent()

    data class GetDetailDevice(val serverId: UInt, val deviceId: UInt) :
        DetailEdgeDeviceSensorEvent()

    data class GetSensorData(
        val deviceId: UInt,
        val serverId: UInt,
        val startDate: String,
        val endDate: String
    ) : DetailEdgeDeviceSensorEvent()

    data class SetNotificationId(
        val notificationId: UInt
    ) : DetailEdgeDeviceSensorEvent()

    data class GetNotificationDetail(val edgeServerId: UInt, val notificationId: UInt) :
        DetailEdgeDeviceSensorEvent()

    data class SetFilterChart(val filter: FilterChart) : DetailEdgeDeviceSensorEvent()
}