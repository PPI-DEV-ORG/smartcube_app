package com.ppidev.smartcube.presentation.edge_device.update

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEdgeDeviceScreen(
    state: UpdateEdgeDeviceState,
    onEvent: (event: UpdateEdgeDeviceEvent) -> Unit,
    edgeServerId: UInt,
    edgeDeviceId: UInt
) {
    var expandedSourceTypes by remember { mutableStateOf(false) }
    var expandedTypes by remember { mutableStateOf(false) }
    var expandedAssignedModelType by remember { mutableStateOf(false) }
    var expandedAssignedModelIndex by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onEvent(UpdateEdgeDeviceEvent.GetDetailEdgeDevice(edgeServerId, edgeDeviceId))
    }

    LaunchedEffect(key1 = state.edgeDeviceId) {
        onEvent(UpdateEdgeDeviceEvent.GetInstalledModels)
    }

    LazyColumn {
        item {
            CustomInputField(
                text = state.vendorName,
                label = "Vendor Name",
                errorText = "",
                onTextChanged = {
                    onEvent(UpdateEdgeDeviceEvent.OnChangeVendorName(it))
                })
        }

        item {
            CustomInputField(
                text = state.vendorNumber,
                label = "Vendor Serial Number",
                errorText = "",
                onTextChanged = {
                    onEvent(UpdateEdgeDeviceEvent.OnChangeVendorNumber(it))
                })
        }

        item {
            CustomSelectInput(
                expanded = expandedSourceTypes,
                label = "Source Type",
                value = state.sourceType,
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

        item {
            CustomSelectInput(
                expanded = expandedTypes,
                label = "Device Type",
                value = state.type,
                onExpandedChange = { expandedTypes = !expandedTypes },
                onDismissRequest = { expandedTypes = false },
                listMenu = {
                    state.listTypes.map {
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = { Text(it.name) },
                            onClick = {
                                onEvent(UpdateEdgeDeviceEvent.OnChangeType(it.name))
                                expandedTypes = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            )
        }

        item {
            // if source type is USB
            CustomInputField(
                text = state.sourceAddress,
                label = "Source USB Id (ex : /dev/video0)",
                errorText = "",
                onTextChanged = {
                    onEvent(UpdateEdgeDeviceEvent.OnChangeSourceAddress(it))
                })
        }


        item {
            CustomSelectInput(
                expanded = expandedAssignedModelType,
                label = "Model Type",
                value = state.assignedModelTypeValue,
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
            CustomInputField(
                text = state.additionalInfo,
                label = "Additional Info",
                errorText = "",
                onTextChanged = {
                    onEvent(UpdateEdgeDeviceEvent.OnChangeAdditionalInfo(it))
                })
        }

        item {
            Spacer(modifier = Modifier.size(22.dp))
            Button(onClick = {
                onEvent(UpdateEdgeDeviceEvent.HandleEditEdgeDevice)
            }) {
                Text(text = "Save")
            }
        }
    }

}