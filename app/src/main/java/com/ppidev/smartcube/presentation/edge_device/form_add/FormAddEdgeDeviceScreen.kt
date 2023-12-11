package com.ppidev.smartcube.presentation.edge_device.form_add

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.ui.components.modal.DialogApp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAddEdgeDeviceScreen(
    state: FormAddEdgeDeviceState,
    onEvent: (event: FormAddEdgeDeviceEvent) -> Unit,
    edgeServerId: UInt,
    navHostController: NavHostController
) {
    LaunchedEffect(Unit) {
        onEvent(FormAddEdgeDeviceEvent.GetEdgeDevicesInfo(edgeServerId))
    }

    LaunchedEffect(key1 = state.edgeDevicesInfo) {
        onEvent(FormAddEdgeDeviceEvent.GetInstalledModels)
    }

    Column {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (state.step > 1) {
                        onEvent(FormAddEdgeDeviceEvent.SetStepValue(state.step - 1))
                    } else {
                        navHostController.popBackStack()
                    }
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                }
                Text(text = "Add Device")
            }
        })

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp)
        ) {
            when (state.step) {
                1 -> {
                    Step1(
                        onDeviceTypeChange = {
                            onEvent(FormAddEdgeDeviceEvent.OnChangeType(it))
                            onEvent(FormAddEdgeDeviceEvent.SetStepValue(2))
                        }
                    )
                }

                2 -> {
                    Step2(
                        vendorName = state.vendorName,
                        serialNumberName = state.vendorNumber,
                        errorVendor = state.error.vendorName,
                        errorSerialNumber = state.error.vendorNumber,
                        onVendorChange = {
                            onEvent(FormAddEdgeDeviceEvent.OnChangeVendorName(it))
                        },
                        onSerialNumberChange = {
                            onEvent(FormAddEdgeDeviceEvent.OnChangeVendorNumber(it))
                        },
                        onClickNext = {
                            onEvent(FormAddEdgeDeviceEvent.SetStepValue(3))
                        }
                    )
                }

                3 -> {
                    Step3(
                        deviceType = state.type,
                        sourceType = state.sourceType,
                        sourceAddress = state.sourceAddress,
                        modelTypeName = state.assignedModelTypeValue,
                        modelIndexName = state.assignedModelIndexValue,
                        listSourceTypes = state.listSourceTypes,
                        listModelType = state.listModelType,
                        listModel = state.listModel,
                        errorSourceType = state.error.sourceType,
                        errorModelIndex = state.error.assignedModelIndex,
                        errorModelType = state.error.assignedModelType,
                        errorSourceAddress = state.error.sourceAddress,
                        onSourceTypeChange = {
                            onEvent(FormAddEdgeDeviceEvent.OnChangeSourceType(it))
                        },
                        onSourceAddressChange = {
                            onEvent(
                                FormAddEdgeDeviceEvent.OnChangeSourceAddress(
                                    it
                                )
                            )
                        },
                        onModelTypeSelected = { key, value ->
                            onEvent(
                                FormAddEdgeDeviceEvent.OnChangeAssignedModelType(
                                    key, value
                                )
                            )
                        },
                        onModelIndexSelected = { key, value ->
                            onEvent(
                                FormAddEdgeDeviceEvent.OnChangeAssignedModelIndex(
                                    key, value
                                )
                            )
                        },
                        onSave = {
                            onEvent(
                                FormAddEdgeDeviceEvent.HandleAddEdgeDevice
                            )
                        }
                    )
                }
            }
        }
    }

    if (state.isSuccess != null) {
        DialogApp(
            message = if (state.isSuccess) "Success" else "Error",
            contentMessage = state.message,
            isSuccess = state.isSuccess,
            onDismiss = {}) {
            if (state.isSuccess) {
                onEvent(FormAddEdgeDeviceEvent.CloseDialog)
                navHostController.popBackStack()
            } else {
                onEvent(FormAddEdgeDeviceEvent.CloseDialog)
            }
        }
    }

    BackHandler {
        if (state.step > 1) {
            onEvent(FormAddEdgeDeviceEvent.SetStepValue(state.step - 1))
        } else {
            if (state.edgeDevicesInfo != null) {
                onEvent(FormAddEdgeDeviceEvent.UnsubscribeFromMqttTopic(state.edgeDevicesInfo.mqttSubTopic))
            }
            navHostController.popBackStack()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (state.edgeDevicesInfo != null) {
                onEvent(FormAddEdgeDeviceEvent.UnsubscribeFromMqttTopic(state.edgeDevicesInfo.mqttSubTopic))
            }
        }
    }
}