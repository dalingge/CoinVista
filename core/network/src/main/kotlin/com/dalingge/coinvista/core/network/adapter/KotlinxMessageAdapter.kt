package com.dalingge.coinvista.core.network.adapter

import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okio.ByteString.Companion.decodeHex
import okio.ByteString.Companion.toByteString
import java.lang.reflect.Type

class KotlinxMessageAdapter<T> private constructor(
    private val json: Json,
    private val serializer: KSerializer<T>
) : MessageAdapter<T> {

    override fun fromMessage(message: Message): T {
        val stringValue = when (message) {
            is Message.Text -> message.value
            is Message.Bytes -> {
                val byteString = message.value.toByteString(0, message.value.size)
                if (byteString.startsWith(UTF8_BOM)) {
                    byteString.substring(UTF8_BOM.size).utf8()
                } else {
                    byteString.utf8()
                }
            }
        }
        return json.decodeFromString(serializer, stringValue)
    }

    override fun toMessage(data: T): Message {
        val stringValue = json.encodeToString(serializer, data)
        return Message.Text(stringValue)
    }

    class Factory(
        private val json: Json = DEFAULT_JSON
    ) : MessageAdapter.Factory {

        override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<*> {
            val serializer = serializerOrThrow(type)
            return KotlinxMessageAdapter(json, serializer)
        }

        private fun serializerOrThrow(type: Type): KSerializer<*> {
            return try {
                serializer(type)
            } catch (e: Exception) {
                throw IllegalArgumentException(
                    "No Kotlinx serializer found for type: $type, ensure @Serializable is used.",
                    e
                )
            }
        }
    }

    private companion object {
        private val DEFAULT_JSON = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            isLenient = true
        }
        private val UTF8_BOM = "EFBBBF".decodeHex()
    }
}