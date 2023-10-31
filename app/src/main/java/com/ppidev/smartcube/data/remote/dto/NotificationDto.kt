package com.ppidev.smartcube.data.remote.dto

import com.ppidev.smartcube.domain.model.NotificationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    @SerialName("image")
    val image: String? = null,

    @SerialName("is_viewed")
    val isViewed: Boolean,

    @SerialName("updated_at")
    val updatedAt: String? = null,

    @SerialName("user_id")
    val userId: Int,

    @SerialName("description")
    val description: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("deleted_at")
    val deletedAt: String? = null
)


fun NotificationDto.toNotificationModel(): NotificationModel {
    return NotificationModel(
        title = title,
        imageUrl = image.toString(),
        isViewed = isViewed,
        id = id,
        description = description
    )
}