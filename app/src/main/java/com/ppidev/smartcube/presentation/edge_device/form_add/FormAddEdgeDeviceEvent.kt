package com.ppidev.smartcube.presentation.edge_device.form_add

sealed class FormAddEdgeDeviceEvent {
    object HandleAddEdgeDevice: FormAddEdgeDeviceEvent()
    data class GetEdgeDevicesInfo(val edgeServerId: UInt): FormAddEdgeDeviceEvent()
    object GetInstalledModels: FormAddEdgeDeviceEvent()
    data class OnChangeVendorName(val str: String): FormAddEdgeDeviceEvent()
    data class OnChangeVendorNumber(val str: String): FormAddEdgeDeviceEvent()
    data class OnChangeType(val str: String): FormAddEdgeDeviceEvent()
    data class OnChangeSourceType(val str: String): FormAddEdgeDeviceEvent()
    data class OnChangeDevSourceId(val str: String): FormAddEdgeDeviceEvent()
    data class OnChangeRtspSourceAddress(val str: String): FormAddEdgeDeviceEvent()
    data class OnChangeAssignedModelType(val num: UInt, val value: String): FormAddEdgeDeviceEvent()
    data class OnChangeAssignedModelIndex(val num: UInt, val value: String): FormAddEdgeDeviceEvent()
    data class OnChangeAdditionalInfo(val str: String): FormAddEdgeDeviceEvent()
}