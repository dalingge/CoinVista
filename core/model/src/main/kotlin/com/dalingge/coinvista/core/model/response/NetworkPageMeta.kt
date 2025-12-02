package com.dalingge.coinvista.core.model.response

import kotlinx.serialization.Serializable

/**
 * 分页模型
 */
@Serializable
data class NetworkPageMeta(
    /**
     * 总条数
     */
    val itemCount: Int? = null,

    /**
     * 每页显示条数
     */
    val limit: Int? = null,

    /**
     * 当前页码
     */
    val page: Int? = null,

    /**
     * 总页数
     */
    val pageCount: Int? = null,

    /**
     * 是否有上一页
     */
    val hasPreviousPage: Boolean = false,

    /**
     * 是否有下一页
     */
    val hasNextPage: Boolean = false,
)