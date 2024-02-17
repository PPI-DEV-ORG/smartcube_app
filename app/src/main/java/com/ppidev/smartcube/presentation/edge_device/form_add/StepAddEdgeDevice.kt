package com.ppidev.smartcube.presentation.edge_device.form_add

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ppidev.smartcube.R
import com.ppidev.smartcube.domain.model.DeviceSourceType
import com.ppidev.smartcube.domain.model.DeviceType
import com.ppidev.smartcube.domain.model.TypeEdgeDevice
import com.ppidev.smartcube.ui.components.card.CardTypeDevice
import com.ppidev.smartcube.ui.components.form.AppInput
import com.ppidev.smartcube.ui.components.form.CustomSelectInput
import com.ppidev.smartcube.ui.components.form.InputLabel


@Composable
fun Step1(onDeviceTypeChange: (str: String) -> Unit) {
    val devices: List<DeviceType> =
        listOf(
            DeviceType(
                id = 0,
                name = TypeEdgeDevice.CAMERA.typeName,
                icon = painterResource(id = R.drawable.ic_camera)
            ),
            DeviceType(
                id = 1,
                name = TypeEdgeDevice.SENSOR.typeName,
                icon = painterResource(id = R.drawable.ic_sensor)
            )
        )

    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        itemsIndexed(items = devices, key = { _, item -> item.id }) { _, item ->
            CardTypeDevice(name = item.name, onClick = {
                onDeviceTypeChange(item.name)
            }, icon = {
                if (item.icon != null) {
                    Icon(
                        painter = item.icon,
                        contentDescription = "device",
                        modifier = Modifier.size(44.dp)
                    )
                }
            })
        }
    }
}

@Composable
fun Step2(
    modifier: Modifier = Modifier,
    vendorName: String,
    serialNumberName: String,
    errorVendor: String,
    errorSerialNumber: String,
    onVendorChange: (str: String) -> Unit,
    onSerialNumberChange: (str: String) -> Unit,
    onClickNext: () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        AppInput(
            text = vendorName,
            customLabel = {
                InputLabel(label = "Vendor Name", isRequired = true)
            },
            errorText = errorVendor,
            onTextChanged = {
                onVendorChange(it)
            },
            placeholder = "Enter vendor device name",
        )

        Spacer(modifier = Modifier.size(16.dp))

        AppInput(
            text = serialNumberName,
            customLabel = {
                InputLabel(label = "Serial Number", isRequired = true)
            },
            errorText = errorSerialNumber,
            onTextChanged = {
                onSerialNumberChange(it)
            },
            placeholder = "Enter serial number device",
        )

        Spacer(modifier = Modifier.size(38.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            Button(modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF79747E),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .width(158.dp), colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ), onClick = {
                onClickNext()
            }) {
                Text(text = "Next")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step3(
    modifier: Modifier = Modifier,
    deviceType: String,
    sourceType: String,
    sourceAddress: String,
    modelTypeName: String,
    modelIndexName: String,
    listSourceTypes: List<DeviceSourceType>,
    listModelType: Map<Int, String>,
    listModel: Map<Int, String>,
    errorSourceType: String,
    errorModelIndex: String,
    errorModelType: String,
    errorSourceAddress: String,
    onSourceAddressChange: (str: String) -> Unit,
    onSourceTypeChange: (value: String) -> Unit,
    onModelTypeSelected: (id: UInt, value: String) -> Unit,
    onModelIndexSelected: (id: UInt, value: String) -> Unit,
    onSave: () -> Unit
) {
    var expandedSourceTypes by remember { mutableStateOf(false) }
    var expandedAssignedModelType by remember { mutableStateOf(false) }
    var expandedAssignedModelIndex by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        CustomSelectInput(expanded = expandedSourceTypes,
            value = sourceType,
            errorText = errorSourceAddress,
            onExpandedChange = { expandedSourceTypes = !expandedSourceTypes },
            onDismissRequest = { expandedSourceTypes = false },
            customLabel = {
                InputLabel(label = "Source Type", isRequired = true)
            },
            listMenu = {
                listSourceTypes.map {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(it.name) },
                        onClick = {
                            onSourceTypeChange(it.name)
                            expandedSourceTypes = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            })

        Spacer(modifier = Modifier.size(14.dp))

        AppInput(
            text = sourceAddress,
            customLabel = {
                InputLabel(label = "Source Address", isRequired = true)
            },
            errorText = errorSourceAddress,
            onTextChanged = {
                onSourceAddressChange(it)
            },
            placeholder = "source address device : IP / Usb / RTSP",
        )

        Spacer(modifier = Modifier.size(14.dp))

        CustomSelectInput(
            expanded = expandedAssignedModelType,
            value = modelTypeName,
            errorText = errorModelType,
            onExpandedChange = { expandedAssignedModelType = !expandedAssignedModelType },
            onDismissRequest = { expandedAssignedModelType = false },
            customLabel = {
                InputLabel(label = "Model Type", isRequired = true)
            },
            listMenu = {
                listModelType.map {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(it.value) },
                        onClick = {
                            onModelTypeSelected(it.key.toUInt(), it.value)
                            expandedAssignedModelType = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        )

        Spacer(modifier = Modifier.size(14.dp))

        CustomSelectInput(
            expanded = expandedAssignedModelIndex,
            value = modelIndexName,
            errorText = errorModelIndex,
            onExpandedChange = { expandedAssignedModelIndex = !expandedAssignedModelIndex },
            onDismissRequest = { expandedAssignedModelIndex = false },
            customLabel = {
                InputLabel(label = "Model Name", isRequired = true)
            },
            listMenu = {
                listModel.map {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(it.value) },
                        onClick = {
                            onModelIndexSelected(it.key.toUInt(), it.value)
                            expandedAssignedModelIndex = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        )

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            onSave()
        }) {
            Text(text = "Save")
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun FormAddEDgeDeviceContentViewPreview() {
    Step1(onDeviceTypeChange = {})
}