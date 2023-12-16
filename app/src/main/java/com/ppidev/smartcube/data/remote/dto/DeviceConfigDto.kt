package com.ppidev.smartcube.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.ppidev.smartcube.domain.model.DeviceConfigModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceConfigDto(
    @SerializedName("type")
    val type: String,

    @SerializedName("usb_id")
    val usbId: String,

    @SerializedName("rtsp_address")
    val rtspAddress: String,

    @SerializedName("source_type")
    val sourceType: String,

    @SerializedName("assigned_model_type")
    val assignedModelType: String,

    @SerializedName("assigned_model_index")
    val assignedModelIndex: Float,

    @SerializedName("additional_info")
    val additionalInfo: AdditionalInfo
) {
    @Serializable
    data class AdditionalInfo(
        @SerializedName("device_location")
        val deviceLocation: String,

        @SerializedName("device_location_coordinate")
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