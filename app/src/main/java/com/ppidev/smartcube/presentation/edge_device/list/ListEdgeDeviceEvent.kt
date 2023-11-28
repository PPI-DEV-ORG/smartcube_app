package com.ppidev.smartcube.presentation.edge_device.list

sealed class ListEdgeDeviceEvent {
    data class GetListEdgeDevice(val edgeServerId: UInt) : ListEdgeDeviceEvent()
    object GetLstEdgeServer : ListEdgeDeviceEvent()

    data class SetEdgeServerId(val edgeServerId: UInt) : ListEdgeDeviceEvent()

    object ListenMqttClient: ListEdgeDeviceEvent()
    object DisconnectMqtt: ListEdgeDeviceEvent()

    object UnsubscribeFromTopicMqtt: ListEdgeDeviceEvent()
}