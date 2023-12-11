package com.ppidev.smartcube.presentation.edge_server.detail

import com.ppidev.smartcube.data.remote.dto.EdgeDevice
import com.ppidev.smartcube.data.remote.dto.EdgeDevicesInfoDto
import com.ppidev.smartcube.data.remote.dto.ServerStatusDto

data class DetailEdgeServerState(
    val isLoading: Boolean = false,
    val edgeDevicesInfo: EdgeDevicesInfoDto? = null,
    val serverInfo: ServerStatusDto? = null,
    val devices: List<EdgeDevice> = emptyList(),
    val isDialogOpen: Boolean = false
)