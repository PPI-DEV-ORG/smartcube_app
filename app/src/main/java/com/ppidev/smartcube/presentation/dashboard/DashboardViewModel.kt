package com.ppidev.smartcube.presentation.dashboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ppidev.smartcube.domain.service.HiveMqttService


class DashboardViewModel : ViewModel() {
    var state by mutableStateOf(DashboardState())

    private val mqttService = HiveMqttService.getInstance()

    fun subscribeToMqttClient() {
        mqttService.subscribeToTopic(HiveMqttService.MQTT_TOPIC, callback = {
            Log.d("PAYLOAD : ", String(it.payloadAsBytes))
        })
    }
}