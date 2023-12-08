package com.ppidev.smartcube.presentation.edge_device.form_add

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
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IAddEdgeDevicesUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IEdgeDevicesInfoUseCase
import com.ppidev.smartcube.utils.CommandMqtt
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormAddEdgeDeviceViewModel @Inject constructor(
    private val addEdgeDevicesUseCase: Lazy<IAddEdgeDevicesUseCase>,
    private val edgeDevicesInfoUseCase: Lazy<IEdgeDevicesInfoUseCase>,
    private val mqttService: Lazy<IMqttService>,
) : ViewModel() {
    var state by mutableStateOf(FormAddEdgeDeviceState())
        private set

    private val gson = Gson()

    fun onEvent(event: FormAddEdgeDeviceEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                FormAddEdgeDeviceEvent.HandleAddEdgeDevice -> {
                    handleAddEdgeDevice()
                }

                is FormAddEdgeDeviceEvent.OnChangeAdditionalInfo -> {
                    state = state.copy(
                        additionalInfo = event.str
                    )
                }

                is FormAddEdgeDeviceEvent.OnChangeAssignedModelIndex -> {
                    state = state.copy(
                        assignedModelIndex = event.num,
                        assignedModelIndexValue = event.value
                    )
                }

                is FormAddEdgeDeviceEvent.OnChangeAssignedModelType -> {
                    state = state.copy(
                        assignedModelType = event.num,
                        assignedModelTypeValue = event.value
                    )
                }

                is FormAddEdgeDeviceEvent.OnChangeSourceType -> {
                    state = state.copy(
                        sourceType = event.str
                    )
                }

                is FormAddEdgeDeviceEvent.OnChangeType -> {
                    state = state.copy(
                        type = event.str
                    )
                }

                is FormAddEdgeDeviceEvent.OnChangeVendorName -> {
                    state = state.copy(
                        vendorName = event.str
                    )
                }

                is FormAddEdgeDeviceEvent.OnChangeVendorNumber -> {
                    state = state.copy(
                        vendorNumber = event.str
                    )
                }

                is FormAddEdgeDeviceEvent.GetEdgeDevicesInfo -> {
                    state = state.copy(
                        edgeServerId = event.edgeServerId
                    )
                    getEdgeDevicesInfo(event.edgeServerId)
                }

                is FormAddEdgeDeviceEvent.GetInstalledModels -> {
                    getListModels()
                }

                is FormAddEdgeDeviceEvent.SetStepValue -> {
                    if (event.step in 1..3) {
                        val errorState = when (event.step) {
                            1 -> FormAddEdgeDeviceState.FormAddEdgeDeviceError()
                            3 -> FormAddEdgeDeviceState.FormAddEdgeDeviceError(
                                vendorName = if (state.vendorName.isEmpty()) "Cannot be empty" else "",
                                vendorNumber = if (state.vendorNumber.isEmpty()) "Cannot be empty" else ""
                            )

                            else -> FormAddEdgeDeviceState.FormAddEdgeDeviceError()
                        }

                        state = if (errorState.hasError()) {
                            state.copy(error = errorState)
                        } else {
                            state.copy(step = event.step)
                        }
                    }
                }

                is FormAddEdgeDeviceEvent.OnChangeSourceAddress -> {
                    state = state.copy(
                        sourceAddress = event.str
                    )
                }

                FormAddEdgeDeviceEvent.CloseDialog -> {
                    state = state.copy(
                        isSuccess = null
                    )
                }
            }
        }
    }

    private suspend fun handleAddEdgeDevice() {
        val edgeServerId = state.edgeServerId
        val assignedModelType = state.assignedModelType
        val assignedModelIndex = state.assignedModelIndex

        val errorState = FormAddEdgeDeviceState.FormAddEdgeDeviceError(
            sourceAddress = if (state.sourceAddress.isEmpty()) "Cannot be empty" else "",
            assignedModelIndex = if (assignedModelIndex == null) "Cannot be empty" else "",
            assignedModelType = if (assignedModelType == null) "Cannot be empty" else ""
        )

        if (errorState.hasError()) {
            state = state.copy(
                error = errorState
            )
            return
        }

        if (edgeServerId != null && assignedModelIndex != null && assignedModelType != null) {
            addEdgeDevicesUseCase.get().invoke(
                edgeServerId = edgeServerId,
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
                        Log.d("ERR", it.message.toString())
                        state = state.copy(
                            isLoading = false,
                            isSuccess = false,
                            message = it.message ?: "Error"
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        Log.d("ADD", it.data?.data.toString())
                        state = state.copy(
                            isLoading = false,
                            isSuccess = true,
                            message = it.data?.message ?: "Success added new device"
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private suspend fun getEdgeDevicesInfo(edgeServerId: UInt) {
        edgeDevicesInfoUseCase.get().invoke(edgeServerId).onEach {
            when (it) {
                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    val response = it.data?.data

                    if (response != null) {
                        state = state.copy(
                            edgeDevicesInfo = response
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getListModels() {
        viewModelScope.launch {
            val mqttSubTopic = state.edgeDevicesInfo?.mqttSubTopic
            val mqttPubTopic = state.edgeDevicesInfo?.mqttPubTopic

            if (mqttSubTopic?.isNotEmpty() == true && mqttPubTopic?.isNotEmpty() == true) {
                mqttService.get().subscribeToTopic(mqttSubTopic) { topic, msg ->
                    val json = String(msg.payload)
                    val result = gson.fromJson<Map<String, Any>>(
                        json,
                        object : TypeToken<Map<String, Any>>() {}.type
                    )

                    val installedModelsResponse = result["data"]

                    Log.d("MQTT RES from $topic : ", "$msg")
                }

                mqttService.get().publishToTopic(
                    mqttPubTopic,
                    CommandMqtt.GET_INSTALLED_MODEL
                )
            }
        }
    }
}