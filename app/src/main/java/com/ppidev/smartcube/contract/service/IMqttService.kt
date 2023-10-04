package com.ppidev.smartcube.contract.service

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish

interface IMqttService {
    fun connect()
    fun subscribeToTopic(topic: String, callback: (data: Mqtt5Publish) -> Unit)
    fun publishToTopic(topic: String, message: String)
}