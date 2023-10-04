package com.ppidev.smartcube.domain.service

import android.util.Log
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5ConnAckException
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import com.ppidev.smartcube.contract.service.IMqttService
import java.util.UUID

class HiveMqttService : IMqttService {

    private var client: Mqtt5BlockingClient = Mqtt5Client.builder()
        .identifier(UUID.randomUUID().toString())
        .serverHost(SERVER_HOST)
        .serverPort(SERVER_PORT)
        .buildBlocking()

    override fun connect() {
        try {
            client.connect()
        } catch (e: Mqtt5ConnAckException) {
            println(e.printStackTrace())
        }
    }

    override fun subscribeToTopic(topic: String, callback: (data: Mqtt5Publish) -> Unit) {
        client.toAsync()
            .subscribeWith()
            .topicFilter(topic)
            .qos(MqttQos.AT_LEAST_ONCE)
            .retainAsPublished(true)
            .callback { x: Mqtt5Publish ->
                callback(x)
            }
            .send()
            .join()
    }

    override fun publishToTopic(topic: String, message: String) {
        client.toAsync()
            .publishWith()
            .topic(topic)
            .payload(message.toByteArray())
            .qos(MqttQos.AT_LEAST_ONCE)
            .send()
            .join()
    }

    init {
        connect()
    }

    companion object {
        private const val SERVER_HOST = "broker.emqx.io"
        private const val SERVER_PORT = 1883
        private const val TAG = "HiveMqttService"
        const val MQTT_TOPIC = "python/mqtt_smartCupe13212"

        @Volatile
        private var INSTANCE: HiveMqttService? = null

        fun getInstance(): HiveMqttService = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HiveMqttService().also {
                INSTANCE = it
            }
        }
    }
}