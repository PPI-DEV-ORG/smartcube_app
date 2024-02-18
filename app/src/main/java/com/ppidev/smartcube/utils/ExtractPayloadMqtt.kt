package com.ppidev.smartcube.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.Json
import org.eclipse.paho.client.mqttv3.MqttMessage

fun extractCommandAndDataMqtt(msg: MqttMessage): Pair<String, Any?> {
    val gson = Gson()
    val json = String(msg.payload)
    val result = gson.fromJson<Map<String, Any>>(
        json,
        object : TypeToken<Map<String, Any>>() {}.type
    )
    val command = Json.decodeFromString<String>(gson.toJson(result["command"]))
    val data = result["data"]
    return Pair(command, data)
}

inline fun <reified T> convertJsonToDto(data: Any?): T? {
    val gson = Gson()
    return data?.let {
        Json.decodeFromString(gson.toJson(data))
    }
}
