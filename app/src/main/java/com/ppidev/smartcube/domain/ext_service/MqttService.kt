package com.ppidev.smartcube.domain.ext_service

import android.content.Context
import android.util.Log
import com.ppidev.smartcube.BuildConfig
import com.ppidev.smartcube.R
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import com.ppidev.smartcube.utils.EHttpCode
import com.ppidev.smartcube.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttMessageListener
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.io.BufferedInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.Security
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.UUID
import javax.inject.Inject
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

class MqttService @Inject constructor(@ApplicationContext private val context: Context) :
    IMqttService {

    private val mqttClient = MqttClient(
        "ssl://${BuildConfig.MQTT_SERVER_HOST}:${BuildConfig.MQTT_SERVER_PORT}",
        UUID.randomUUID().toString(),
        MemoryPersistence()
    )

    override fun connect(): MqttClient? {
        try {
            val sslSocketFactory =
                getSingleSocketFactory(context.resources.openRawResource(R.raw.mqtt_ssl))
            val mqttConnectOptions = MqttConnectOptions()

            mqttConnectOptions.socketFactory = sslSocketFactory
            mqttConnectOptions.userName = BuildConfig.MQTT_USERNAME
            mqttConnectOptions.password = BuildConfig.MQTT_PASSWORD.toCharArray()
            mqttClient.connect(mqttConnectOptions)

            mqttClient.setCallback(object : MqttCallback {
                // Called when the client lost the connection to the broker
                override fun connectionLost(cause: Throwable) {
                    println("client lost connection $cause")
                }

                override fun messageArrived(topic: String, message: MqttMessage) {
                }

                // Called when an outgoing publish is complete
                override fun deliveryComplete(token: IMqttDeliveryToken) {
                    println("delivery complete $token")
                }
            })

            Log.d("MQTT", "connected")
            return mqttClient
        } catch (e: Exception) {
            Log.e("Exception", "Failed to connect MQTT : " + e.localizedMessage)
            return null
        }
    }

    override fun subscribeToTopic(
        topic: String,
        callback: (topic: String, data: MqttMessage) -> Unit
    ): Resource<Boolean> {
        if (!checkIfMqttIsConnected()) {
            return Resource.Error(503, "MQTT Client is not Connected", false)
        }

        mqttClient.subscribe(topic, 1, IMqttMessageListener { topic, message ->
            callback(topic, message)
        })
        return Resource.Success(true)
    }

    override fun publishToTopic(topic: String, message: String): Resource<Boolean> {
        if (!checkIfMqttIsConnected()) {
            return Resource.Error(
                EHttpCode.ServiceUnavailable.code,
                "MQTT Client is not connected",
                false
            )
        }

        mqttClient.publish(topic, MqttMessage(message.toByteArray()))
        return Resource.Success(true)
    }

    override fun unsubscribeFromTopic(topic: String) {
        try {
            mqttClient.unsubscribe(topic)
        } catch (e: Exception) {
            Log.d("Exception", "Failed Unsubscribe Topic : ${e.localizedMessage}")
        }
    }

    override fun disconnect() {
        try {
            mqttClient.disconnect()
            Log.d("MQTT", "Success disconnected to MQTT Server")
        } catch (e: Exception) {
            Log.d("Exception", "Failed Disconnect MQTT : ${e.localizedMessage}")
        }
    }

    private fun getSingleSocketFactory(caCrtFileInputStream: InputStream?): SSLSocketFactory? {
        try {
            Security.addProvider(BouncyCastleProvider())

            var caCert: X509Certificate? = null
            val bis = BufferedInputStream(caCrtFileInputStream)
            val cf = CertificateFactory.getInstance("X.509")

            while (bis.available() > 0) {
                caCert = cf.generateCertificate(bis) as X509Certificate
            }

            val caKs = KeyStore.getInstance(KeyStore.getDefaultType())

            caKs.load(null, null)
            caKs.setCertificateEntry("cert-certificate", caCert)

            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf.init(caKs)

            val sslContext = SSLContext.getInstance("TLSv1.2")

            sslContext.init(null, tmf.trustManagers, null)

            return sslContext.socketFactory
        } catch (e: Exception) {
            return null
        }
    }

    override fun checkIfMqttIsConnected(): Boolean {
        return mqttClient.isConnected
    }
}