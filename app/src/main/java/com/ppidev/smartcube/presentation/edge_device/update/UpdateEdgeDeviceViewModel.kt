package com.ppidev.smartcube.presentation.edge_device.update

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.utils.Resource
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IUpdateEdgeDeviceUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IViewEdgeDeviceUseCase
import com.ppidev.smartcube.data.remote.dto.EdgeServerInstalledModelDto
import com.ppidev.smartcube.utils.MQTTCommand
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
class UpdateEdgeDeviceViewModel @Inject constructor(
    private val viewEdgeDeviceUseCase: Lazy<IViewEdgeDeviceUseCase>,
    private val updateEdgeDeviceUseCase: Lazy<IUpdateEdgeDeviceUseCase>,
    private val mqttService: Lazy<IMqttService>
) : ViewModel() {
    var state by mutableStateOf(UpdateEdgeDeviceState())
        private set

    fun onEvent(event: UpdateEdgeDeviceEvent) {
        viewModelScope.launch {
            when (event) {
                is UpdateEdgeDeviceEvent.HandleEditEdgeDevice -> {
                    handleUpdateDevice(event.edgeDeviceId, event.edgeServerId)
                }

                is UpdateEdgeDeviceEvent.OnChangeAdditionalInfo -> {
                    state = state.copy(
                        additionalInfo = event.str
                    )
                }

                is UpdateEdgeDeviceEvent.OnChangeAssignedModelIndex -> {
                    state = state.copy(
                        assignedModelIndex = event.num,
                        assignedModelIndexValue = event.value
                    )
                }

                is UpdateEdgeDeviceEvent.OnChangeAssignedModelType -> {
                    state = state.copy(
                        assignedModelType = event.num,
                        assignedModelTypeValue = event.value
                    )
                }

                is UpdateEdgeDeviceEvent.OnChangeSourceAddress -> {
                    state = state.copy(
                        sourceAddress = event.str
                    )
                }

                is UpdateEdgeDeviceEvent.OnChangeSourceType -> {
                    state = state.copy(
                        sourceType = event.str,
                    )
                }

                is UpdateEdgeDeviceEvent.OnChangeType -> {
                    state = state.copy(
                        type = event.str
                    )
                }

                is UpdateEdgeDeviceEvent.OnChangeVendorName -> {
                    state = state.copy(
                        vendorName = event.str
                    )
                }

                is UpdateEdgeDeviceEvent.OnChangeVendorNumber -> {
                    state = state.copy(
                        vendorNumber = event.str
                    )
                }

                is UpdateEdgeDeviceEvent.GetInstalledModels -> {
                    getInstalledModel(event.topic)
                }

                is UpdateEdgeDeviceEvent.GetDetailEdgeDevice -> {
                    getDetailEdgeDevice(event.edgeServerId, event.edgeDeviceId)
                }

                is UpdateEdgeDeviceEvent.SubscribeToTopic -> {
                    subscribeToTopicMqtt(event.topic)
                }

                is UpdateEdgeDeviceEvent.UnsubscribeToMqttService -> {
                    unsubscribeToTopicMqtt(event.topic)
                }

                is UpdateEdgeDeviceEvent.SetShowAlertDialog -> {
                    state = state.copy(
                        isShowAlert = event.status
                    )
                }

                is UpdateEdgeDeviceEvent.SetShowDialog -> {
                    state = state.copy(
                        isShowDialog = event.status
                    )
                }

                is UpdateEdgeDeviceEvent.ValidateForm -> {
                    event.callback(validateForm())
                }

                is UpdateEdgeDeviceEvent.SetAssignedModelIndex -> {
                    try {
                        if (state.listModel.isNotEmpty()) {
                            if (event.index.toInt() <= state.listModel.count()) {
                                state = state.copy(
                                    assignedModelIndex = event.index,
                                    assignedModelIndexValue = state.listModel[event.index.toInt()]
                                        ?: ""
                                )
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private suspend fun handleUpdateDevice(edgeDeviceId: UInt, edgeServerId: UInt) {
        val assignedModelType = state.assignedModelType ?: return
        val assignedModelIndex = state.assignedModelIndex ?: return

        updateEdgeDeviceUseCase.get().invoke(
            edgeServerId = edgeServerId,
            edgeDeviceId = edgeDeviceId,
            vendorName = state.vendorName,
            vendorNumber = state.vendorNumber,
            type = state.type,
            sourceType = state.sourceType,
            sourceAddress = state.sourceAddress,
            assignedModelType = assignedModelType,
            assignedModelIndex = assignedModelIndex,
            additionalInfo = state.additionalInfo
        ).onEach {
            when (it) {
                is Resource.Error -> {
                    Log.d("EDIT_ERR", "${it.statusCode} : " + it.message.toString())
                    state = state.copy(
                        isLoading = false,
                        isShowDialog = false,
                        errors = UpdateEdgeDeviceState.Error(
                            message = it.message ?: "Failed Update Device"
                        )
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    Log.d("EDIT_SUCESSS", it.data?.data.toString())
                    state = state.copy(
                        isLoading = false,
                        isShowDialog = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getDetailEdgeDevice(serverId: UInt, deviceId: UInt) {
        viewEdgeDeviceUseCase.get().invoke(edgeServerId = serverId, edgeDeviceId = deviceId)
            .onEach {
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
                        val data = it.data?.data
                        if (data != null) {
                            state = state.copy(
                                vendorName = data.vendorName,
                                vendorNumber = data.vendorNumber.toString(),
                                sourceType = data.sourceType,
                                type = data.type.toString(),
                                sourceAddress = data.sourceAddress,
                                assignedModelType = data.assignedModelType?.toUInt(),
                                assignedModelIndex = data.assignedModelIndex?.toUInt(),
                                assignedModelTypeValue = state.listModelType[data.assignedModelType]
                                    ?: "",
                                isLoading = false
                            )
                        } else {
                            state = state.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun validateForm(): Boolean {
        val errorState = UpdateEdgeDeviceState.Error(
            sourceAddress = if (state.sourceAddress.isEmpty()) "Cannot be empty" else "",
            assignedModelIndex = if (state.assignedModelIndex == null) "Cannot be empty" else "",
            assignedModelType = if (state.assignedModelType == null) "Cannot be empty" else "",
            vendorNumber = if (state.vendorNumber.isEmpty()) "Cannot be empty" else "",
            vendorName = if (state.vendorName.isEmpty()) "Cannot be empty" else "",
            sourceType = if (state.sourceType.isEmpty()) "Cannot be empty" else ""
        )

        if (errorState.hasError()) {
            state = state.copy(
                errors = errorState
            )
            return false
        }
        return true
    }

    private fun subscribeToTopicMqtt(topic: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!mqttService.get().checkIfMqttIsConnected()) {
                mqttService.get().connect()
            }

            mqttService.get().subscribeToTopic(topic) { _, msg ->
                val (command, data) = extractCommandAndDataMqtt(msg)
                when (command) {
                    MQTTCommand.GET_INSTALLED_MODEL -> {
                        val dataDecoded = convertJsonToDto<List<EdgeServerInstalledModelDto>>(data)
                        Log.d("TEST", dataDecoded.toString())
                        if (dataDecoded != null) {
                            val dataMap: Map<Int, String> =
                                dataDecoded.mapIndexed { index, mlModelDto ->
                                    index to mlModelDto.name
                                }.toMap()

                            state = state.copy(
                                listModel = dataMap
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getInstalledModel(topic: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!mqttService.get().checkIfMqttIsConnected()) {
                mqttService.get().connect()
            }

            mqttService.get().publishToTopic(topic, MQTTCommand.GET_INSTALLED_MODEL)
        }
    }

    private fun unsubscribeToTopicMqtt(topic: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!mqttService.get().checkIfMqttIsConnected()) {
                mqttService.get().connect()
            }

            mqttService.get().unsubscribeFromTopic(topic)
        }
    }
}

sealed class UpdateEdgeDeviceEvent {
    data class HandleEditEdgeDevice(val edgeDeviceId: UInt, val edgeServerId: UInt): UpdateEdgeDeviceEvent()
    data class GetInstalledModels(val topic: String): UpdateEdgeDeviceEvent()
    data class SubscribeToTopic(val topic: String): UpdateEdgeDeviceEvent()
    data class UnsubscribeToMqttService(val topic: String): UpdateEdgeDeviceEvent()
    data class GetDetailEdgeDevice(val edgeServerId: UInt, val edgeDeviceId: UInt):  UpdateEdgeDeviceEvent()
    data class OnChangeVendorName(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeVendorNumber(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeType(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeSourceType(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeSourceAddress(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeAssignedModelType(val num: UInt, val value: String): UpdateEdgeDeviceEvent()
    data class OnChangeAssignedModelIndex(val num: UInt, val value: String): UpdateEdgeDeviceEvent()
    data class OnChangeAdditionalInfo(val str: String): UpdateEdgeDeviceEvent()
    data class SetAssignedModelIndex(val index: UInt): UpdateEdgeDeviceEvent()
    data class SetShowAlertDialog(val status: Boolean): UpdateEdgeDeviceEvent()
    data class SetShowDialog(val status: Boolean?): UpdateEdgeDeviceEvent()
    data class ValidateForm(val callback: (status: Boolean) -> Unit): UpdateEdgeDeviceEvent()
}