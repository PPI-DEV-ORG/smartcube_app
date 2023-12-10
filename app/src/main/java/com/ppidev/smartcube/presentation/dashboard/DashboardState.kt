package com.ppidev.smartcube.presentation.dashboard

import com.ppidev.smartcube.data.remote.dto.EdgeDevice
import com.ppidev.smartcube.domain.model.ServerStatusModel

data class DashboardState(
    val edgeServerId: UInt? = null,
    val username: String = "",
    val email: String = "",
    val serverInfoMQTT: ServerStatusModel? = null,
    val listServer: List<String> = emptyList(),
    val listServerId: List<UInt> = emptyList(),
    val listDevices: List<EdgeDevice> = emptyList(),
    val mqttPublishTopic: String? = null,
    val mqttSubscribeTopic: String? = null,
    val error: Error = Error(),
    val loading: Loading = Loading()
) {
    data class Error(
        val message: String = "",
        val listServerError: String = "",
        val listDevicesError: String = ""
    )

    data class Loading(
        val isLoadingListServer: Boolean = false,
        val isLoadingListDevices: Boolean = false
    )
}