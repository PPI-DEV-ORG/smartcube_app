package com.ppidev.smartcubeListSourceDeviceType.presentation.edge_device.form_add

import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import com.ppidev.smartcube.domain.model.DeviceSourceType
import com.ppidev.smartcube.domain.model.DeviceType
import com.ppidev.smartcube.domain.model.ListDeviceType
import com.ppidev.smartcube.domain.model.ListSourceDeviceType

data class FormAddEdgeDeviceState(
    val isLoading: Boolean = false,
    val edgeDevicesInfo : EdgeDevicesInfoDto? = null,

    val edgeServerId: UInt? = null,
    val vendorName: String = "",
    val vendorNumber: String = "",
    val type: String = "",
    val sourceType: String = "",
    val devSourceId: String = "",
    val rtspSourceAddress: String = "",

    val assignedModelType: UInt? = null,
    val assignedModelTypeValue: String = "",
    val assignedModelIndex: UInt? = null,
    val assignedModelIndexValue: String = "",


    val additionalInfo: String = "",
    val lat: String = "",
    val lon: String = "",

    val listSourceTypes: List<DeviceSourceType> = ListSourceDeviceType,
    val listTypes: List<DeviceType> = ListDeviceType,

    val listModelType: Map<Int, String> = mapOf(
        0 to "Realtime Object Detection",
    ),
    val listModel: Map<Int, String> = mapOf(
        0 to "Fire and smoke detection-v1"
    )
)