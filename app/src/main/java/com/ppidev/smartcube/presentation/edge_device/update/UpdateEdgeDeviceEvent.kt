package com.ppidev.smartcube.presentation.edge_device.update


sealed class UpdateEdgeDeviceEvent {
    object HandleEditEdgeDevice: UpdateEdgeDeviceEvent()
    object GetInstalledModels: UpdateEdgeDeviceEvent()
    data class GetDetailEdgeDevice(val edgeServerId: UInt, val edgeDeviceId: UInt):  UpdateEdgeDeviceEvent()
    data class OnChangeVendorName(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeVendorNumber(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeType(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeSourceType(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeSourceAddress(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeAssignedModelType(val num: UInt, val value: String): UpdateEdgeDeviceEvent()
    data class OnChangeAssignedModelIndex(val num: UInt, val value: String): UpdateEdgeDeviceEvent()
    data class OnChangeAdditionalInfo(val str: String): UpdateEdgeDeviceEvent()
}