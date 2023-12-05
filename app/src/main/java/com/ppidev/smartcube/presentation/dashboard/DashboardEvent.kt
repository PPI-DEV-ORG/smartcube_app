package com.ppidev.smartcube.presentation.dashboard

sealed class DashboardEvent {
    object GetServerInfo: DashboardEvent()
    object UnsubscribeToMqttService : DashboardEvent()
    object GetCurrentWeather : DashboardEvent()
    data class GetDevicesConfig(val serverId: UInt) : DashboardEvent()
    object GetListEdgeServer : DashboardEvent()
    data class SetEdgeServerId(val id: UInt) : DashboardEvent()
}
