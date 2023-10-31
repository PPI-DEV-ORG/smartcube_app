package com.ppidev.smartcube.data.local.serializer

import androidx.datastore.core.Serializer
import com.ppidev.smartcube.data.local.entity.TokenAppEntity
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object TokenAppSerializer : Serializer<TokenAppEntity> {
    override val defaultValue: TokenAppEntity
        get() = TokenAppEntity()

    override suspend fun readFrom(input: InputStream): TokenAppEntity {
        return try {
            Json.decodeFromString(
                deserializer = TokenAppEntity.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: TokenAppEntity, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = TokenAppEntity.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}