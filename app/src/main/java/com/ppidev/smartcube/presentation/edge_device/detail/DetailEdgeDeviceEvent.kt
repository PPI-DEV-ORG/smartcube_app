package com.ppidev.smartcube.presentation.edge_device.detail

sealed class DetailEdgeDeviceEvent {
    object StartEdgeDevice : DetailEdgeDeviceEvent()
    object RestartEdgeDevice : DetailEdgeDeviceEvent()
    data class GetDetailDevice(val serverId: UInt, val deviceId: UInt) : DetailEdgeDeviceEvent()
    data class GetDetailNotification(val serverId: UInt, val notificationId: UInt) :
        DetailEdgeDeviceEvent()
    data class SetNotificationId(val notificationId: UInt) : DetailEdgeDeviceEvent()
    data class SetDeviceInfo(
        val edgeServerId: UInt,
        val processId: Int,
    ) : DetailEdgeDeviceEvent()

    object HandleCloseDialogMsg : DetailEdgeDeviceEvent()
}