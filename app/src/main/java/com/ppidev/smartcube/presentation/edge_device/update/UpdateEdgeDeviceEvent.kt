package com.ppidev.smartcube.presentation.edge_device.update


sealed class UpdateEdgeDeviceEvent {
    data class HandleEditEdgeDevice(val edgeDeviceId: UInt, val edgeServerId: UInt): UpdateEdgeDeviceEvent()
    data class GetInstalledModels(val topic: String): UpdateEdgeDeviceEvent()
    data class SubscribeToTopic(val topic: String): UpdateEdgeDeviceEvent()
    data class UnsubscribeToMqttService(val topic: String): UpdateEdgeDeviceEvent()
    data class GetDetailEdgeDevice(val edgeServerId: UInt, val edgeDeviceId: UInt):  UpdateEdgeDeviceEvent()
    data class OnChangeVendorName(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeVendorNumber(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeType(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeSourceType(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeSourceAddress(val str: String): UpdateEdgeDeviceEvent()
    data class OnChangeAssignedModelType(val num: UInt, val value: String): UpdateEdgeDeviceEvent()
    data class OnChangeAssignedModelIndex(val num: UInt, val value: String): UpdateEdgeDeviceEvent()
    data class OnChangeAdditionalInfo(val str: String): UpdateEdgeDeviceEvent()
    data class SetAssignedModelIndex(val index: UInt): UpdateEdgeDeviceEvent()
    data class SetShowAlertDialog(val status: Boolean): UpdateEdgeDeviceEvent()
    data class SetShowDialog(val status: Boolean?): UpdateEdgeDeviceEvent()
    data class ValidateForm(val callback: (status: Boolean) -> Unit): UpdateEdgeDeviceEvent()
}