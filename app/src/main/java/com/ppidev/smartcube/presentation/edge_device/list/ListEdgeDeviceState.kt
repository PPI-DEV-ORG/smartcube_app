package com.ppidev.smartcube.presentation.edge_device.list

import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto

data class ListEdgeDeviceState(
    val edgeDevicesInfo: EdgeDevicesInfoDto? = null,
    val isLoading: Boolean = false,
    val listEdgeServer: List<EdgeServerItemDto> = emptyList(),
    val serverId: UInt? = null
)