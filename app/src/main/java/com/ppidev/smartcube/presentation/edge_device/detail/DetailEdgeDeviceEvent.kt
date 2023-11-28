package com.ppidev.smartcube.presentation.edge_device.detail

sealed class DetailEdgeDeviceEvent {
    object GetDetailDevice : DetailEdgeDeviceEvent()
    object StartEdgeDevice : DetailEdgeDeviceEvent()
    object RestartEdgeDevice : DetailEdgeDeviceEvent()
    data class SetDeviceInfo(
        val edgeServerId: UInt,
        val processId: Int,
    ) : DetailEdgeDeviceEvent()

    object HandleCloseDialogMsg : DetailEdgeDeviceEvent()
}