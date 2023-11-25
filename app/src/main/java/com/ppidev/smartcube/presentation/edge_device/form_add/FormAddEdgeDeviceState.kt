package com.ppidev.smartcube.presentation.edge_device.form_add

import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto

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

    val listSourceTypes: Map<String, String> = mapOf("usb" to "USB", "lan" to "LAN"),
    val listTypes: Map<String, String> = mapOf("camera" to "CAMERA", "sensor" to "SENSOR"),

    val listModelType: Map<Int, String> = mapOf(
        0 to "Realtime Object Detection",
    ),
    val listModel: Map<Int, String> = mapOf(
        0 to "Fire and smoke detection-v1"
    )
)