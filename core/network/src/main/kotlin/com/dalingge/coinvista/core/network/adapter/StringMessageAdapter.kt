package com.dalingge.coinvista.core.network.adapter

import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import com.tinder.scarlet.utils.getRawType
import okio.ByteString.Companion.toByteString
import java.lang.reflect.Type

class StringMessageAdapter : MessageAdapter<String> {

    override fun fromMessage(message: Message): String {
        val stringValue = when (message) {
            is Message.Text -> message.value
            is Message.Bytes -> {
                val byteString = message.value.toByteString(0, message.value.size)
                byteString.utf8()
            }
        }
        return stringValue
    }

    override fun toMessage(data: String): Message {
        return Message.Text(data)
    }

    class Factory : MessageAdapter.Factory {

        override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<String> {
            if (type.getRawType() == String::class.java) {
                return StringMessageAdapter()
            }
            throw IllegalArgumentException("Type is not supported by this MessageAdapterFactory: $type")
        }

    }
}