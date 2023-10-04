package com.ppidev.smartcube.data.local.serializer

import androidx.datastore.core.Serializer
import com.ppidev.smartcube.data.local.dataclass.AppSettingModel
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object AppSettingSerializer : Serializer<AppSettingModel> {
    override val defaultValue: AppSettingModel
        get() = AppSettingModel()

    override suspend fun readFrom(input: InputStream): AppSettingModel {
        return try {
            Json.decodeFromString(
                deserializer = AppSettingModel.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppSettingModel, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = AppSettingModel.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}