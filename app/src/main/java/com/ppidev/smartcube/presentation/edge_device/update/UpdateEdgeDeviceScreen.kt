package com.ppidev.smartcube.presentation.edge_device.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.ui.components.form.AppInput
import com.ppidev.smartcube.ui.components.form.CustomSelectInput
import com.ppidev.smartcube.ui.components.modal.DialogApp
import com.ppidev.smartcube.ui.components.modal.SimpleAlertDialog
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEdgeDeviceScreen(
    state: UpdateEdgeDeviceState,
    onEvent: (event: UpdateEdgeDeviceEvent) -> Unit,
    edgeServerId: UInt,
    edgeDeviceId: UInt,
    mqttPubTopic: String,
    mqttSubTopic: String,
    navHostController: NavHostController
) {
    var expandedSourceTypes by remember { mutableStateOf(false) }
//    var expandedTypes by remember { mutableStateOf(false) }
    var expandedAssignedModelType by remember { mutableStateOf(false) }
    var expandedAssignedModelIndex by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onEvent(UpdateEdgeDeviceEvent.SubscribeToTopic(mqttSubTopic))
        onEvent(UpdateEdgeDeviceEvent.GetDetailEdgeDevice(edgeServerId, edgeDeviceId))
    }

    LaunchedEffect(key1 = state.listModel) {
        if (state.assignedModelIndex != null) {
            onEvent(UpdateEdgeDeviceEvent.SetAssignedModelIndex(state.assignedModelIndex))
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            onEvent(UpdateEdgeDeviceEvent.GetInstalledModels(mqttPubTopic))
        }
    }
    Column {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                }
                Text(text = "Update Device")
            }
        })

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                AppInput(
                    text = state.vendorName,
                    label = "Vendor Name",
                    placeholder = "Enter device vendor name",
                    errorText = state.errors.vendorName,
                    onTextChanged = {
                        onEvent(UpdateEdgeDeviceEvent.OnChangeVendorName(it))
                    })
            }

            item {
                AppInput(
                    text = state.vendorNumber,
                    label = "Vendor Serial Number",
                    placeholder = "Enter serial number device",
                    errorText = state.errors.vendorNumber,
                    onTextChanged = {
                        onEvent(UpdateEdgeDeviceEvent.OnChangeVendorNumber(it))
                    })
            }

            item {
                CustomSelectInput(
                    expanded = expandedSourceTypes,
                    label = "Source Type",
                    value = state.sourceType,
                    errorText = state.errors.sourceType,
                    onExpandedChange = { expandedSourceTypes = !expandedSourceTypes },
                    onDismissRequest = { expandedSourceTypes = false },
                    listMenu = {
                        state.listSourceTypes.map {
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = { Text(it.name) },
                                onClick = {
                                    onEvent(UpdateEdgeDeviceEvent.OnChangeSourceType(it.name))
                                    expandedSourceTypes = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                )
            }

//            item {
//                CustomSelectInput(
//                    expanded = expandedTypes,
//                    label = "Device Type",
//                    value = state.type,
//                    onExpandedChange = { expandedTypes = !expandedTypes },
//                    onDismissRequest = { expandedTypes = false },
//                    listMenu = {
//                        state.listTypes.map {
//                            DropdownMenuItem(
//                                modifier = Modifier.fillMaxWidth(),
//                                text = { Text(it.name) },
//                                onClick = {
//                                    onEvent(UpdateEdgeDeviceEvent.OnChangeType(it.name))
//                                    expandedTypes = false
//                                },
//                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
//                            )
//                        }
//                    }
//                )
//            }

            item {
                AppInput(
                    text = state.sourceAddress,
                    label = "Source address",
                    placeholder = "Enter source address device : IP/RTSP/USB",
                    errorText = state.errors.sourceAddress,
                    onTextChanged = {
                        onEvent(UpdateEdgeDeviceEvent.OnChangeSourceAddress(it))
                    })
            }

            item {
                CustomSelectInput(
                    expanded = expandedAssignedModelType,
                    label = "Model Type",
                    value = state.assignedModelTypeValue,
                    errorText = state.errors.assignedModelType,
                    onExpandedChange = { expandedAssignedModelType = !expandedAssignedModelType },
                    onDismissRequest = { expandedAssignedModelType = false },
                    listMenu = {
                        state.listModelType.map {
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = { Text(it.value) },
                                onClick = {
                                    onEvent(
                                        UpdateEdgeDeviceEvent.OnChangeAssignedModelType(
                                            it.key.toUInt(),
                                            it.value
                                        )
                                    )
                                    expandedAssignedModelType = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                )
            }

            item {
                CustomSelectInput(
                    expanded = expandedAssignedModelIndex,
                    label = "Model Name",
                    errorText = state.errors.assignedModelIndex,
                    value = state.assignedModelIndexValue,
                    onExpandedChange = { expandedAssignedModelIndex = !expandedAssignedModelIndex },
                    onDismissRequest = { expandedAssignedModelIndex = false },
                    listMenu = {
                        state.listModel.map {
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = { Text(it.value) },
                                onClick = {
                                    onEvent(
                                        UpdateEdgeDeviceEvent.OnChangeAssignedModelIndex(
                                            it.key.toUInt(),
                                            it.value
                                        )
                                    )
                                    expandedAssignedModelIndex = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.size(24.dp))
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    onEvent(UpdateEdgeDeviceEvent.ValidateForm {
                        if (it) {
                            onEvent(UpdateEdgeDeviceEvent.SetShowAlertDialog(true))
                        }
                    })
                }, enabled = !state.isLoading) {
                    Text(text = "Save")
                }
            }
        }
    }

    SimpleAlertDialog(show = state.isShowAlert, message = state.messageAlert, onDismiss = {
        onEvent(UpdateEdgeDeviceEvent.SetShowAlertDialog(false))
    }) {
        onEvent(UpdateEdgeDeviceEvent.HandleEditEdgeDevice(edgeDeviceId, edgeServerId))
        onEvent(UpdateEdgeDeviceEvent.SetShowAlertDialog(false))
    }

    if (state.isShowDialog != null) {
        DialogApp(
            message = if (state.isShowDialog) "Success" else "Failed",
            contentMessage = if (state.isShowDialog) "Success Update Data" else state.errors.message,
            isSuccess = state.isShowDialog,
            onDismiss = { }) {
            if (state.isShowDialog) {
                navHostController.popBackStack()
            } else {
                onEvent(UpdateEdgeDeviceEvent.SetShowDialog(null))
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            onEvent(UpdateEdgeDeviceEvent.UnsubscribeToMqttService(mqttSubTopic))
        }
    }
}