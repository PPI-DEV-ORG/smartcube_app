package com.ppidev.smartcube.presentation.edge_server.list

import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto

data class ListEdgeServerState(
    val isLoading: Boolean = false,
    val listEdgeServer: List<EdgeServerItemDto> = emptyList()
)