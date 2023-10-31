package com.ppidev.smartcube.data.remote.dto

import com.ppidev.smartcube.domain.model.DeviceConfigModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceConfigDto(
    @SerialName("type")
    val type: String,

    @SerialName("usb_id")
    val usbId: String,

    @SerialName("rtsp_address")
    val rtspAddress: String,

    @SerialName("source_type")
    val sourceType: String,

    @SerialName("assigned_model_type")
    val assignedModelType: String,

    @SerialName("assigned_model_index")
    val assignedModelIndex: Float,

    @SerialName("additional_info")
    val additionalInfo: AdditionalInfo
) {
    @Serializable
    data class AdditionalInfo(
        @SerialName("device_location")
        val deviceLocation: String,

        @SerialName("device_location_coordinate")
        val deviceLocationCoordinate: String
    )
}


fun DeviceConfigDto.toDeviceConfigModel(): DeviceConfigModel {
    return DeviceConfigModel(
        type = type,
        usbId = usbId,
        rtspAddress = rtspAddress,
        sourceType = sourceType,
        assignedModelType = assignedModelType,
        assignedModelIndex = assignedModelIndex,
        additionalInfo = DeviceConfigModel.AdditionalInfo(
            deviceLocation = additionalInfo.deviceLocation,
            deviceLocationCoordinate = additionalInfo.deviceLocationCoordinate
        )
    )
}