package com.ppidev.smartcube.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class EdgeDeviceSensorDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("edge_server_id")
    val edgeServerId: Int,
    @SerializedName("device_id")
    val deviceId: Int,
    @SerializedName("data_measured")
    val dataMeasured: List<SensorData>,
    @SerializedName("inference_label_status")
    val inferenceLabelStatus: String,
    @SerializedName("captured_at")
    val capturedAt: String
)

@Serializable
data class SensorData(
    @SerializedName("data")
    val data: Double,
    @SerializedName("sensor_type")
    val sensorType: String,
    @SerializedName("unit_measure")
    val unitMeasure: String
)

data class MeasuredSensor(
    val capturedAt: String,
    val dataValue: Double
)