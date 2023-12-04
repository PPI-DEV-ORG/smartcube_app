package com.ppidev.smartcube.presentation.edge_device.update

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.domain.use_case.edge_device.IUpdateEdgeDeviceUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdateEdgeDeviceViewModel @Inject constructor(
    private val updateEdgeDeviceUseCase: Lazy<IUpdateEdgeDeviceUseCase>
) : ViewModel() {
    var state by mutableStateOf(UpdateEdgeDeviceState())
        private set

    fun onEvent(event: UpdateEdgeDeviceEvent) {
        viewModelScope.launch {
            when (event) {
                UpdateEdgeDeviceEvent.GetInstalledModels -> {}
                UpdateEdgeDeviceEvent.HandleEditEdgeDevice -> {
                    handleUpdateDevice()
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

                is UpdateEdgeDeviceEvent.OnChangeDevSourceId -> {
                    state = state.copy(
                        devSourceId = event.str
                    )
                }

                is UpdateEdgeDeviceEvent.OnChangeRtspSourceAddress -> {
                    state = state.copy(
                        rtspSourceAddress = event.str
                    )
                }

                is UpdateEdgeDeviceEvent.OnChangeSourceType -> {
                    state = state.copy(
                        sourceType = event.str,
                        rtspSourceAddress = "",
                        devSourceId = ""
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
                }

                is UpdateEdgeDeviceEvent.GetDetailEdgeDevice ->  {
                    state = state.copy(
                        edgeServerId = event.edgeServerId,
                        edgeDeviceId = event.edgeDeviceId
                    )
                }
            }
        }
    }

    private suspend fun handleUpdateDevice() {
        val edgeServerId = state.edgeServerId
        val edgeDeviceId = state.edgeDeviceId
        val assignedModelType = state.assignedModelType
        val assignedModelIndex = state.assignedModelIndex
//        Log.d("LOG", edgeServerId.toString())
//        Log.d("LOG", edgeDeviceId.toString())

        if (edgeServerId != null && assignedModelIndex != null && assignedModelType != null && edgeDeviceId != null) {
            updateEdgeDeviceUseCase.get().invoke(
                edgeServerId = edgeServerId,
                edgeDeviceId = edgeDeviceId,
                vendorName = state.vendorName,
                vendorNumber = state.vendorNumber,
                type = state.type,
                sourceType = state.sourceType,
                devSourceId = state.devSourceId,
                rtspSourceAddress = state.rtspSourceAddress,
                assignedModelType = assignedModelType,
                assignedModelIndex = assignedModelIndex,
                additionalInfo = state.additionalInfo
            ).onEach {
                when (it) {
                    is Resource.Error -> {
                        Log.d("EDIT_ERR", "${it.statusCode} : " + it.message.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Log.d("EDIT_SUCESSS", it.data?.data.toString())
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}