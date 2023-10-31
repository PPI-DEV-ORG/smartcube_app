package com.ppidev.smartcube.presentation.dashboard.model

import kotlinx.serialization.Serializable

object CommandMqtt {
    const val GET_SERVER_INFO = "/hostDeviceStatus"
    const val GET_INSTALLED_MODEL = "/getInstalledModels"
    const val RESTART_DEVICE = "/restartDevice"
    const val GET_DEVICES_CONFIG ="/getDeviceConfig"
}

