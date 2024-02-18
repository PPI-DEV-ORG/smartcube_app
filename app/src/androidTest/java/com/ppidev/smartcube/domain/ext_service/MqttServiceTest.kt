package com.ppidev.smartcube.domain.ext_service

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.ppidev.smartcube.utils.Resource
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class MqttServiceIntegrationTest {
    private lateinit var mqttService: MqttService
    private lateinit var context: Context

    private val testTopic = "test_topic"

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        mqttService = MqttService(context)
    }

    @After
    fun tearDown() {
        // Disconnect from the MQTT server after each test
        mqttService.disconnect()
    }

    @Test
    fun testMqttConnection() {
        val latch = CountDownLatch(1)
        val mqttClient = mqttService.connect()

        assert(mqttClient != null)

        // Wait for the latch for a reasonable amount of time
        latch.await(5, TimeUnit.SECONDS)

        // Assert that the connection was successful
        assert(mqttService.checkIfMqttIsConnected())
    }

    @Test
    fun testMqttPublish() {
        val latch = CountDownLatch(1)
        var receivedMessage: String? = null

        val mqttClient = mqttService.connect()

        assert(mqttClient != null)

        // Subscribe to a topic to receive the published message
        mqttService.subscribeToTopic(testTopic) { _, message ->
            // Callback when a message is received
            receivedMessage = String(message.payload)
            latch.countDown()
        }

        // Publish a message to the topic
        val publishResult = mqttService.publishToTopic(testTopic, "Hello, MQTT!")

        latch.await(5, TimeUnit.SECONDS)

        assert(publishResult is Resource.Success)
        assert(receivedMessage == "Hello, MQTT!")
    }
}