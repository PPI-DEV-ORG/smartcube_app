package com.ppidev.smartcube.presentation.edge_server.detail

sealed class DetailEdgeServerEvent {
    data class GetDetailDevicesInfo(val edgeServerId: UInt): DetailEdgeServerEvent()
    data class SetDialogStatus(val status: Boolean): DetailEdgeServerEvent()
    object ListenMqtt: DetailEdgeServerEvent()
    object UnListenMqtt: DetailEdgeServerEvent()
}