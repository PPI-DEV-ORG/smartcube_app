package com.ppidev.smartcube.presentation.dashboard

sealed class DashboardEvent {
    object SubscribeToMqttService : DashboardEvent()

    object GetListNotification : DashboardEvent()
    object GetServerSummary : DashboardEvent()
    object GetListModelInstalled : DashboardEvent()

    object GetDeviceConfig : DashboardEvent()

    object UnsubscribeToMqttService : DashboardEvent()
}
