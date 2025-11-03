package com.dalingge.coinvista.core.model.response

import kotlinx.serialization.Serializable

/**
 * 解析socket响应
 */
@Serializable
data class WebsocketResponse(
    val method:String = "",
    val id: Int = -1,
   // val result: Any? =null
)
