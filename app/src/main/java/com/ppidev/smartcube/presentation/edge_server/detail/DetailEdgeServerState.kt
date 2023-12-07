package com.ppidev.smartcube.presentation.edge_server.detail

import com.ppidev.smartcube.data.remote.dto.DeviceConfigDto
import com.ppidev.smartcube.data.remote.dto.ServerStatusDto
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto

data class DetailEdgeServerState(
    val isLoading: Boolean = false,
    val edgeDevicesInfo: EdgeDevicesInfoDto? = null,
    val serverInfo: ServerStatusDto? = null,
    val devices: List<DeviceConfigDto> = emptyList(),
    val isDialogOpen: Boolean = false
)