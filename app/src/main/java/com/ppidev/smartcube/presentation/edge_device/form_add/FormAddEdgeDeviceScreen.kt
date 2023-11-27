package com.ppidev.smartcube.presentation.edge_device.form_add

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ppidev.smartcube.ui.components.form.CustomInputField
import com.ppidev.smartcube.ui.components.form.CustomSelectInput
import com.ppidev.smartcubeListSourceDeviceType.presentation.edge_device.form_add.FormAddEdgeDeviceState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAddEdgeDeviceScreen(
    state: FormAddEdgeDeviceState,
    onEvent: (event: FormAddEdgeDeviceEvent) -> Unit,
    edgeServerId: UInt
) {
    var expandedSourceTypes by remember { mutableStateOf(false) }
    var expandedTypes by remember { mutableStateOf(false) }
    var expandedAssignedModelType by remember { mutableStateOf(false) }
    var expandedAssignedModelIndex by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onEvent(FormAddEdgeDeviceEvent.GetEdgeDevicesInfo(edgeServerId))
    }

    LaunchedEffect(key1 = state.edgeDevicesInfo) {
        onEvent(FormAddEdgeDeviceEvent.GetInstalledModels)
    }

    LazyColumn {
        item {
            CustomInputField(
                text = state.vendorName,
                label = "Vendor Name",
                errorText = "",
                onTextChanged = {
                    onEvent(FormAddEdgeDeviceEvent.OnChangeVendorName(it))
                })
        }

        item {
            CustomInputField(
                text = state.vendorNumber,
                label = "Vendor Serial Number",
                errorText = "",
                onTextChanged = {
                    onEvent(FormAddEdgeDeviceEvent.OnChangeVendorNumber(it))
                })
        }

        item {
            CustomSelectInput(
                expanded = expandedSourceTypes,
                label = "Source Type",
                value = state.sourceType,
                onExpandedChange = { expandedSourceTypes = !expandedSourceTypes },
                onDismissRequest = { expandedSourceTypes = false },
            ) {
                state.listSourceTypes.map {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(it.name) },
                        onClick = {
                            onEvent(FormAddEdgeDeviceEvent.OnChangeSourceType(it.name))
                            expandedSourceTypes = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        item {
            CustomSelectInput(
                expanded = expandedTypes,
                label = "Device Type",
                value = state.type,
                onExpandedChange = { expandedTypes = !expandedTypes },
                onDismissRequest = { expandedTypes = false },
            ) {
                state.listTypes.map {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(it.name) },
                        onClick = {
                            onEvent(FormAddEdgeDeviceEvent.OnChangeType(it.name))
                            expandedTypes = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        item {
            // if source type is USB
            if (state.sourceType == "usb") {
                CustomInputField(
                    text = state.devSourceId,
                    label = "Source USB Id (ex : /dev/video/0)",
                    errorText = "",
                    onTextChanged = {
                        onEvent(FormAddEdgeDeviceEvent.OnChangeDevSourceId(it))
                    })
            }
        }

        item {
            // if source type is RTSP
            if (state.sourceType =="lan") {
                CustomInputField(
                    text = state.rtspSourceAddress,
                    label = "RTSP Url",
                    errorText = "",
                    onTextChanged = {
                        onEvent(FormAddEdgeDeviceEvent.OnChangeRtspSourceAddress(it))
                    })
            }
        }

        item {
            CustomSelectInput(
                expanded = expandedAssignedModelType,
                label = "Model Type",
                value = state.assignedModelTypeValue,
                onExpandedChange = { expandedAssignedModelType = !expandedAssignedModelType },
                onDismissRequest = { expandedAssignedModelType = false },
            ) {
                state.listModelType.map {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(it.value) },
                        onClick = {
                            onEvent(FormAddEdgeDeviceEvent.OnChangeAssignedModelType(it.key.toUInt(),  it.value))
                            expandedAssignedModelType = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        item {
            CustomSelectInput(
                expanded = expandedAssignedModelIndex,
                label = "Model Name",
                value = state.assignedModelIndexValue,
                onExpandedChange = { expandedAssignedModelIndex = !expandedAssignedModelIndex },
                onDismissRequest = { expandedAssignedModelIndex = false },
            ) {
                state.listModel.map {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(it.value) },
                        onClick = {
                            onEvent(FormAddEdgeDeviceEvent.OnChangeAssignedModelIndex(it.key.toUInt(), it.value))
                            expandedAssignedModelIndex = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        item {
            CustomInputField(
                text = state.additionalInfo,
                label = "Additional Info",
                errorText = "",
                onTextChanged = {
                    onEvent(FormAddEdgeDeviceEvent.OnChangeAdditionalInfo(it))
                })
        }

        item {
            Spacer(modifier = Modifier.size(22.dp))
            Button(onClick = {
                onEvent(FormAddEdgeDeviceEvent.HandleAddEdgeDevice)
            }) {
                Text(text = "Add Devices")
            }
        }
    }
}