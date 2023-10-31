package com.ppidev.smartcube.presentation.dashboard

import com.ppidev.smartcube.domain.model.DeviceConfigModel
import com.ppidev.smartcube.domain.model.NotificationModel
import com.ppidev.smartcube.domain.model.DeviceStatusModel
import com.ppidev.smartcube.domain.model.MLModel

data class DashboardState(
    val isLoadingNotification: Boolean = false,
    val isLoadingServerSummary: Boolean = false,
    val serverSummary: DeviceStatusModel = DeviceStatusModel("0", "0", "0", "0",  "0"),
    val notifications: List<NotificationModel> = emptyList(),
    val listModelsML: List<MLModel> = emptyList(),
    val listDevicesConfig: List<DeviceConfigModel> = emptyList()
)