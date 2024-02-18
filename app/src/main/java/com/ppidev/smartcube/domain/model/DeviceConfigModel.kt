package com.ppidev.smartcube.domain.model

data class DeviceConfigModel(
    val type: String,
    val usbId: String,
    val rtspAddress: String,
    val sourceType: String,
    val assignedModelType: String,
    val assignedModelIndex: Float,
    val additionalInfo: AdditionalInfo
) {
    data class AdditionalInfo(
        val deviceLocation: String,
        val deviceLocationCoordinate: String
    )
}