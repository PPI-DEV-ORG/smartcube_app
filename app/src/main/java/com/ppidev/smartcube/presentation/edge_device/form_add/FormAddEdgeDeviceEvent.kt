package com.ppidev.smartcube.presentation.edge_device.form_add

sealed class FormAddEdgeDeviceEvent {
    object HandleAddEdgeDevice : FormAddEdgeDeviceEvent()
    object CloseDialog : FormAddEdgeDeviceEvent()

    data class GetInstalledModels(val topic: String) : FormAddEdgeDeviceEvent()
    data class SubscribeToMqttTopic(val topic: String) : FormAddEdgeDeviceEvent()
    data class UnsubscribeFromMqttTopic(val topic: String) : FormAddEdgeDeviceEvent()
    data class GetEdgeDevicesInfo(val edgeServerId: UInt) : FormAddEdgeDeviceEvent()
    data class OnChangeVendorName(val str: String) : FormAddEdgeDeviceEvent()
    data class OnChangeVendorNumber(val str: String) : FormAddEdgeDeviceEvent()
    data class OnChangeType(val str: String) : FormAddEdgeDeviceEvent()
    data class OnChangeSourceType(val str: String) : FormAddEdgeDeviceEvent()
    data class OnChangeAssignedModelType(val num: UInt, val value: String) :
        FormAddEdgeDeviceEvent()

    data class OnChangeAssignedModelIndex(val num: UInt, val value: String) :
        FormAddEdgeDeviceEvent()

    data class OnChangeAdditionalInfo(val str: String) : FormAddEdgeDeviceEvent()
    data class SetStepValue(val step: Int) : FormAddEdgeDeviceEvent()
    data class OnChangeSourceAddress(val str: String) : FormAddEdgeDeviceEvent()
}