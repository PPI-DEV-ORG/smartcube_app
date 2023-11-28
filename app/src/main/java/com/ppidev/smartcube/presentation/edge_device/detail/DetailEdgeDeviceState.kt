package com.ppidev.smartcube.presentation.edge_device.detail

data class DetailEdgeDeviceState(
    val isLoading: Boolean = false,
    val edgeServerId: UInt? = null,
    val processId: Int? = null,
    val isDialogMsgOpen: Boolean = false,
    val messageDialog: String = "",
    val isSuccess: Boolean = false
)