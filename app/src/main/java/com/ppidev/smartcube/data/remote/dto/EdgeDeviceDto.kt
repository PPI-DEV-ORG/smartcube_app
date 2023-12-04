package com.ppidev.smartcube.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreateEdgeDeviceDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("vendor_name")
    val vendorName: String,

    @SerializedName("vendor_number")
    val vendorNumber: String? = "",

    @SerializedName("type")
    val type: String? = "",

    @SerializedName("source_type")
    val sourceType: String? ="",

    @SerializedName("dev_source_id")
    val devSourceId: String? = "",

    @SerializedName("rtsp_source_address")
    val rtspSourceAddress: String? ="",

    @SerializedName("assigned_model_type")
    val assignedModelType: UInt,

    @SerializedName("assigned_model_index")
    val assignedModelIndex: UInt,

    @SerializedName("additional_info")
    val additionalInfo: String? = "",
)


@Serializable
data class DeviceEdgeServer(
    @SerializedName("edge_server_id")
    val edgeServerId: Int,

    @SerializedName("device_id")
    val deviceId: Int,
)

@Serializable
data class EdgeDevice(
    @SerializedName("id")
    val id: Int,

    @SerializedName("vendor_name")
    val vendorName: String,

    @SerializedName("vendor_number")
    val vendorNumber: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("source_type")
    val sourceType: String,

    @SerializedName("dev_source_id")
    val devSourceId: String,

    @SerializedName("rtsp_source_address")
    val rtspSourceAddress: String,

    @SerializedName("assigned_model_type")
    val assignedModelType: Int,

    @SerializedName("assigned_model_index")
    val assignedModelIndex: Int,

    @SerializedName("additional_info")
    val additionalInfo: String,

    @SerializedName("DeviceEdgeServer")
    val deviceEdgeServer: DeviceEdgeServer,
)

@Serializable
data class EdgeDevicesInfoDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = "",

    @SerializedName("vendor")
    val vendor: String? = "",

    @SerializedName("description")
    val description: String? = "",

    @SerializedName("mqtt_user")
    val mqttUser: String? = "",

    @SerializedName("mqtt_password")
    val mqttPassword: String? = "",

    @SerializedName("mqtt_pub_topic")
    val mqttPubTopic: String? = "",

    @SerializedName("mqtt_sub_topic")
    val mqttSubTopic: String? = "",

    @SerializedName("devices")
    val devices: List<EdgeDevice> = emptyList(),
)