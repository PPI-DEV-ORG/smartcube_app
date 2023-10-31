package com.ppidev.smartcube.data.local.serializer

import androidx.datastore.core.Serializer
import com.ppidev.smartcube.data.local.entity.AppSettingEntity
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object AppSettingSerializer : Serializer<AppSettingEntity> {
    override val defaultValue: AppSettingEntity
        get() = AppSettingEntity()

    override suspend fun readFrom(input: InputStream): AppSettingEntity {
        return try {
            Json.decodeFromString(
                deserializer = AppSettingEntity.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppSettingEntity, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = AppSettingEntity.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}