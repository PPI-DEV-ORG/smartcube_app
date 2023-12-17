package com.ppidev.smartcube.presentation.edge_device.update

import com.ppidev.smartcube.domain.model.DeviceSourceType
import com.ppidev.smartcube.domain.model.DeviceType
import com.ppidev.smartcube.domain.model.ListDeviceType
import com.ppidev.smartcube.domain.model.ListSourceDeviceType

data class UpdateEdgeDeviceState(
    val isShowAlert: Boolean = false,
    val isShowDialog: Boolean? = null,
    val messageAlert: String = "Do you sure to update this device ?",
    val isLoading: Boolean = false,

    val edgeServerId: UInt? = null,
    val edgeDeviceId: UInt? = null,

    val vendorName: String = "",
    val vendorNumber: String = "",
    val type: String = "",
    val sourceType: String = "",
    val sourceAddress: String = "",

    val assignedModelType: UInt? = null,
    val assignedModelTypeValue: String = "",
    val assignedModelIndex: UInt? = null,
    val assignedModelIndexValue: String = "",

    val additionalInfo: String = "",
    val lat: String = "",
    val lon: String = "",

    val listSourceTypes: List<DeviceSourceType> = ListSourceDeviceType,
    val listTypes: List<DeviceType> = ListDeviceType,

    val listModelType: Map<Int, String> = mapOf(
        0 to "Realtime Object Detection",
        1 to "Data analytic"
    ),

    val listModel: Map<Int, String> = mapOf(
    ),

    val errors: Error = Error()
) {
    data class Error(
        val message: String = "",
        val vendorName: String = "",
        val vendorNumber: String = "",
        val sourceType: String = "",
        val sourceAddress: String = "",
        val assignedModelType: String = "",
        val assignedModelIndex: String = ""
    ) {
        fun hasError(): Boolean {
            return vendorName.isNotEmpty() ||
                    vendorNumber.isNotEmpty() ||
                    sourceType.isNotEmpty() ||
                    assignedModelType.isNotEmpty() ||
                    assignedModelIndex.isNotEmpty() ||
                    sourceAddress.isNotEmpty()
        }
    }
}