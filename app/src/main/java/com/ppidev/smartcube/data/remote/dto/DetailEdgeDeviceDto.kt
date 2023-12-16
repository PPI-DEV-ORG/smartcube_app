package com.ppidev.smartcube.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DetailEdgeDeviceDto(

	@field:SerializedName("additional_info")
	val additionalInfo: String? = null,

	@field:SerializedName("edge_servers")
	val edgeServers: List<EdgeServersItem> = emptyList(),

	@field:SerializedName("source_address")
	val sourceAddress: String = "",

	@field:SerializedName("vendor_name")
	val vendorName: String = "",

	@field:SerializedName("source_type")
	val sourceType: String = "",

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("vendor_number")
	val vendorNumber: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("assigned_model_type")
	val assignedModelType: Int? = null,

	@field:SerializedName("assigned_model_index")
	val assignedModelIndex: Int? = null,

	@field:SerializedName("notifications")
	val notifications: List<NotificationDto> = emptyList()
)


data class EdgeServersItem(

	@field:SerializedName("DeviceEdgeServer")
	val deviceEdgeServer: DeviceEdgeServer? = null,

	@field:SerializedName("vendor")
	val vendor: String = "",

	@field:SerializedName("name")
	val name: String = "",

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int
)
