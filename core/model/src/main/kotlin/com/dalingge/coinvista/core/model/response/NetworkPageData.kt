package com.dalingge.coinvista.core.model.response

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

/**
 * 网络响应分页模型
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class NetworkPageData<T>(
    /**
     * 列表
     */
    @JsonNames("data")
    var result: List<T> = emptyList(),

    /**
     * 分页数据
     */
    var meta: NetworkPageMeta? = null,
)