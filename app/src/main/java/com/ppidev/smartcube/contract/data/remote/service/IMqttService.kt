package com.ppidev.smartcube.contract.data.remote.service

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish

interface IMqttService {
    fun connect(): Unit
    fun subscribeToTopic(topic: String): Unit
    fun publishToTopic(topic: String, message: String): Unit
    fun unsubscribeFromTopic(topic: String): Unit
    fun disconnect(): Unit

    fun listenSubscribedTopic(callback: (data: Mqtt5Publish) -> Unit)
}