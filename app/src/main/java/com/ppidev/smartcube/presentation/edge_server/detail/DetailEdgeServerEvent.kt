package com.ppidev.smartcube.presentation.edge_server.detail

sealed class DetailEdgeServerEvent {
    data class GetDetailDevicesInfo(val edgeServerId: UInt): DetailEdgeServerEvent()

    object ListenMqtt: DetailEdgeServerEvent()

    object UnListenMqtt: DetailEdgeServerEvent()
}