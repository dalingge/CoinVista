package com.dalingge.coinvista.core.model.response

import kotlinx.serialization.Serializable

/**
 * 网络响应分页模型
 */
@Serializable
data class NetworkPageData<T>(
    /**
     * 列表
     */
    var result: List<T> = emptyList(),

    /**
     * 分页数据
     */
    var meta: NetworkPageMeta? = null,
)