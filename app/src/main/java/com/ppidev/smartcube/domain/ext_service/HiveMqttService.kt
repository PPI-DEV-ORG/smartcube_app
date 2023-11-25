package com.ppidev.smartcube.domain.ext_service


//class HiveMqttService @Inject constructor(@ApplicationContext private val context: Context) :
//    IMqttService {
//    private val client: Mqtt5AsyncClient by lazy {
//        Mqtt5Client.builder()
//            .identifier(UUID.randomUUID().toString())
//            .serverHost("ssl://${BuildConfig.MQTT_SERVER_HOST}")
//            .serverPort(BuildConfig.MQTT_SERVER_PORT.toInt())
//            .sslWithDefaultConfig()
//            .advancedConfig()
//            .apply {
//                sslConnection()
//                getSingleSocketFactory(context.resources.openRawResource(R.raw.mqtt_ssl))
//            }
//            .applyAdvancedConfig()
//            .buildAsync()
//    }
//
//    override fun connect() {
//        try {
//            if (!client.state.isConnectedOrReconnect) {
//                client.connectWith().simpleAuth()
//                    .username(BuildConfig.MQTT_USERNAME)
//                    .password(BuildConfig.MQTT_PASSWORD.toByteArray())
//                    .applySimpleAuth()
//                    .send();
//                Log.d("MQTT", "connected to mqtt")
//            }
//        } catch (e: Mqtt5ConnAckException) {
//            e.printStackTrace()
//        }
//    }
//
//    override fun subscribeToTopic(topic: String) {
//        try {
//            client.toAsync()
//                .subscribeWith()
//                .topicFilter(topic).qos(MqttQos.AT_LEAST_ONCE)
//                .send()
//        } catch (e: Exception) {
//            Log.e("Exception", "Failed Subscribe To Topic : " + e.localizedMessage)
//        }
//    }
//
//    override fun listenSubscribedTopic(callback: (data: Mqtt5Publish) -> Unit) {
//        client.publishes(MqttGlobalPublishFilter.SUBSCRIBED) {
//            callback(it)
//        }
//    }
//
//    override fun publishToTopic(topic: String, message: String) {
//        try {
//            client.toAsync()
//                .publishWith()
//                .topic(topic)
//                .retain(true)
//                .payload(message.toByteArray())
//                .qos(MqttQos.AT_LEAST_ONCE)
//                .send()
//
//        } catch (e: Exception) {
//            Log.e("Exception", "Failed Publish To Topic : " + e.localizedMessage)
//        }
//    }
//
//    override fun unsubscribeFromTopic(topic: String) {
//        try {
//            client.toAsync()
//                .unsubscribeWith()
//                .topicFilter(topic)
//                .send()
//        } catch (e: Exception) {
//            Log.e("Exception", "Failed Unsubscribe To Topic : " + e.localizedMessage)
//        }
//    }
//
//    override fun disconnect() {
//        if (client.state.isConnectedOrReconnect) {
//            client.disconnect()
//            Log.d("MQTT", "MQTT disconnected")
//        }
//    }
//
//    override fun sslConnection() {
//        try {
//            val caCrtFile = context.resources.openRawResource(R.raw.mqtt_ssl)
//            val sslSocketFactory = getSingleSocketFactory(caCrtFile)
//            val mqttClient = MqttClient(
//                "ssl://${BuildConfig.MQTT_SERVER_HOST}:${BuildConfig.MQTT_SERVER_PORT}",
//                UUID.randomUUID().toString(),
//                MemoryPersistence()
//            )
//
//            val mqttConnectOptions = MqttConnectOptions()
//            mqttConnectOptions.socketFactory = sslSocketFactory
//            mqttConnectOptions.userName = BuildConfig.MQTT_USERNAME
//            mqttConnectOptions.password = BuildConfig.MQTT_PASSWORD.toCharArray()
//            mqttClient.connect(mqttConnectOptions)
//
//            Log.d("MQTT", "Connected")
//        } catch (e: Exception) {
//            Log.e("Exception", "Failed connect : " + e.localizedMessage)
//        }
//    }
//
//    private fun getSingleSocketFactory(caCrtFileInputStream: InputStream?): SSLSocketFactory? {
//        Security.addProvider(BouncyCastleProvider())
//
//        var caCert: X509Certificate? = null
//        val bis = BufferedInputStream(caCrtFileInputStream)
//        val cf = CertificateFactory.getInstance("X.509")
//
//        while (bis.available() > 0) {
//            caCert = cf.generateCertificate(bis) as X509Certificate
//        }
//
//        val caKs = KeyStore.getInstance(KeyStore.getDefaultType())
//
//        caKs.load(null, null)
//        caKs.setCertificateEntry("cert-certificate", caCert)
//
//        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
//        tmf.init(caKs)
//
//        val sslContext = SSLContext.getInstance("TLSv1.2")
//
//        sslContext.init(null, tmf.trustManagers, null)
//
//        return sslContext.socketFactory
//    }
//}