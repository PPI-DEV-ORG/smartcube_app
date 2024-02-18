package com.ppidev.smartcube.presentation.edge_server.form_add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IAddEdgeServerUseCase
import com.ppidev.smartcube.utils.Resource
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormAddEdgeServerViewModel @Inject constructor(
    private val addEdgeServer: Lazy<IAddEdgeServerUseCase>
) : ViewModel() {

    var state by mutableStateOf(FormAddEdgeServerState())

    fun onEvent(event: FormAddEdgeServerEvent) {
        viewModelScope.launch {
            when (event) {
                FormAddEdgeServerEvent.HandleAddEdgeServer -> {
                    handleAddEdgeServer()
                }

                is FormAddEdgeServerEvent.OnDescriptionEdgeServerChange -> {
                    state = state.copy(
                        description = event.desc,
                        error = state.error.copy(
                            description = ""
                        )
                    )
                }

                is FormAddEdgeServerEvent.OnNameEdgeServerChange -> {
                    state = state.copy(
                        serverName = event.name,
                        error = state.error.copy(
                            serverName = ""
                        )
                    )
                }

                is FormAddEdgeServerEvent.OnVendorEdgeServerChange -> {
                    state = state.copy(
                        serverVendor = event.vendor,
                        error = state.error.copy(
                            serverVendor = ""
                        )
                    )
                }

                is FormAddEdgeServerEvent.HandleCloseDialog -> {
                    if (state.isSuccess == true) {
                        event.callback()
                    }

                    state = state.copy(
                        isSuccess = null
                    )
                }
            }
        }
    }


    private fun handleAddEdgeServer() {
        if (state.isLoading) {
            return
        }

        val errorState = FormAddEdgeServerState.ErrorFormAddEdgeServer(
            serverName = if (state.serverName.isEmpty()) "Cannot empty" else "",
            serverVendor = if (state.serverVendor.isEmpty()) "Cannot empty" else ""
        )

        if (errorState.hasErrors()) {
            state = state.copy(error = errorState)
            return
        }

        addEdgeServer.get().invoke(
            name = state.serverName,
            description = state.description,
            vendor = state.serverVendor
        ).onEach {
            when (it) {
                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    val responseData = it.data?.data

                    if (responseData != null) {
                        state = state.copy(
                            isLoading = false,
                            message = "Success add to server",
                            isSuccess = true,
                            edgeServerId = responseData.edgeServerId,
                            edgeServerAccessToken = responseData.edgeServerAccessToken,
                            mqttPublishTopic = responseData.mqttPubTopic,
                            mqttSubscribeTopic = responseData.mqttSubTopic
                        )
                    }
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        message = it.message ?: "Something Wrong",
                        isSuccess = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}

sealed class FormAddEdgeServerEvent {
    object HandleAddEdgeServer : FormAddEdgeServerEvent()
    data class OnNameEdgeServerChange(val name: String) : FormAddEdgeServerEvent()
    data class OnDescriptionEdgeServerChange(val desc: String) : FormAddEdgeServerEvent()
    data class OnVendorEdgeServerChange(val vendor: String) : FormAddEdgeServerEvent()
    data class HandleCloseDialog(val callback: () -> Unit) : FormAddEdgeServerEvent()
}