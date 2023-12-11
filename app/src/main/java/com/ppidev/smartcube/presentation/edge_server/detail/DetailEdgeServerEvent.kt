package com.ppidev.smartcube.presentation.edge_server.detail

sealed class DetailEdgeServerEvent {
    data class GetDetailDevicesInfo(val edgeServerId: UInt): DetailEdgeServerEvent()
    data class SetDialogStatus(val status: Boolean): DetailEdgeServerEvent()
    data class SubscribeToTopicMqtt(val topic: String): DetailEdgeServerEvent()
    data class GetServerInfoMqtt(val topic: String): DetailEdgeServerEvent()
    data class UnSubscribeFromMqttTopic(val topic: String): DetailEdgeServerEvent()
}