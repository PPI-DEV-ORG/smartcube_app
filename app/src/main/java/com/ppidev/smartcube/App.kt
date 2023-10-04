package com.ppidev.smartcube

import android.util.Log
import androidx.compose.runtime.Composable
import com.ppidev.smartcube.domain.permission.RequestNotificationPermission
import com.ppidev.smartcube.domain.service.HiveMqttService

@Composable
fun App() {
    SetupPermissions()
//    setupServices()
    BottomAppBar()
}

@Composable
fun SetupPermissions() {
//    RequestNotificationPermission()
}

//fun setupServices() {
//    val mqttService = HiveMqttService.getInstance()
//    mqttService.subscribeToTopic(HiveMqttService.MQTT_TOPIC, callback = {
//        Log.d("PAYLOAD : ", String(it.payloadAsBytes))
//    })
//}
