package com.ppidev.smartcube.presentation.edge_device.detail

import com.ppidev.smartcube.data.remote.dto.DetailEdgeDeviceDto
import com.ppidev.smartcube.domain.model.NotificationModel

data class DetailEdgeDeviceState(
    val isLoading: Boolean = false,
    val isLoadingDetailNotification: Boolean = false,
    val isOpenImageOverlay: Boolean = false,
    val edgeServerId: UInt? = null,
    val notificationId: UInt? = null,
    val notificationDetail: NotificationModel? = null,
    val notifications: List<NotificationModel> = emptyList(),
    val processId: Int? = null,
    val isDialogMsgOpen: Boolean = false,
    val messageDialog: String = "",
    val isSuccess: Boolean = false,
    val edgeDeviceDetail: DetailEdgeDeviceDto? = null
)