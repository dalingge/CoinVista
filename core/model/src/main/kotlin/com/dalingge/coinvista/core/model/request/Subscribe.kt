package com.dalingge.coinvista.core.model.request

import kotlinx.serialization.Serializable

//{
//    "method": "SUBSCRIBE",
//    "args": [
//        "btc@tick"
//    ],
//    "id": 1
//}
@Serializable
data class Subscribe(
    val id: Int = 0,
    val method: String = "",
    val args: List<String> = emptyList(),
)
