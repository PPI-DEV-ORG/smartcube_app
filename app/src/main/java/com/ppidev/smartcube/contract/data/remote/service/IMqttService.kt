package com.ppidev.smartcube.contract.data.remote.service

import com.ppidev.smartcube.utils.Resource
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage

interface IMqttService {
    fun connect(): MqttClient?
    fun subscribeToTopic(topic: String, callback: (topic: String, data: MqttMessage) -> Unit): Resource<Boolean>
    fun publishToTopic(topic: String, message: String): Resource<Boolean>
    fun unsubscribeFromTopic(topic: String): Unit
    fun disconnect(): Unit
    fun checkIfMqttIsConnected(): Boolean
}