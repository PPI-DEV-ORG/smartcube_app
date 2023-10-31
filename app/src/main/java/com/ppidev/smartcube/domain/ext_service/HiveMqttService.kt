package com.ppidev.smartcube.domain.ext_service

import android.util.Log
import com.hivemq.client.mqtt.MqttGlobalPublishFilter
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5ConnAckException
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import com.ppidev.smartcube.BuildConfig
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import java.util.UUID
import javax.inject.Inject

class HiveMqttService @Inject constructor(): IMqttService {
    private val client: Mqtt5AsyncClient by lazy {
        Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost(BuildConfig.MQTT_SERVER_HOST)
            .serverPort(BuildConfig.MQTT_SERVER_PORT.toInt())
            .buildAsync()
    }

    override fun connect() {
        try {
            if (!client.state.isConnectedOrReconnect) {
                client.connect()
                Log.d("MQTT", "connected to mqtt")
            }
        } catch (e: Mqtt5ConnAckException) {
            e.printStackTrace()
        }
    }

    init {
        connect()
    }

    override fun subscribeToTopic(topic: String) {
        try {
            client.toAsync()
                .subscribeWith()
                .topicFilter(topic).qos(MqttQos.AT_LEAST_ONCE)
                .send()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun listenSubscribedTopic(callback: (data: Mqtt5Publish) -> Unit) {
        client.publishes(MqttGlobalPublishFilter.SUBSCRIBED) {
            callback(it)
        }
    }

    override fun publishToTopic(topic: String, message: String) {
        try {
            client.toAsync()
                .publishWith()
                .topic(topic)
                .retain(true)
                .payload(message.toByteArray())
                .qos(MqttQos.AT_LEAST_ONCE)
                .send()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun unsubscribeFromTopic(topic: String) {
        try {
            client.toAsync()
                .unsubscribeWith()
                .topicFilter(topic)
                .send()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun disconnect() {
        if (client.state.isConnectedOrReconnect) {
            client.disconnect()
            Log.d("MQTT", "MQTT disconnected")
        }
    }
}