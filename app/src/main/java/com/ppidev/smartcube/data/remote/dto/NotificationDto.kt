package com.ppidev.smartcube.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.ppidev.smartcube.domain.model.NotificationModel
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    @SerializedName("image")
    val image: String? = null,

    @SerializedName("is_viewed")
    val isViewed: Boolean,

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("edge_server_id")
    val edgeServerId: UInt,

    @SerializedName("device_id")
    val deviceId: UInt,

    @SerializedName("device_type")
    val deviceType: String,

    @SerializedName("object_label")
    val objectLabel: String? = null,

    @SerializedName("risk_level")
    val riskLevel: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("deleted_at")
    val deletedAt: String? = null,
)


fun NotificationDto.toNotificationModel(): NotificationModel {
    return NotificationModel(
        id = id,
        title = title,
        imageUrl = image.toString(),
        isViewed = isViewed,
        description = description,
        createdAt = createdAt,
        deviceType = deviceType,
        objectLabel = objectLabel,
        riskLevel = riskLevel,
        edgeDeviceId = deviceId,
        edgeServerId = edgeServerId
    )
}