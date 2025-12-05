package com.dalingge.coinvista.core.model.entity

import kotlinx.serialization.Serializable

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/5  10:43
 */
@Serializable
data class NewsItem(
    val id: String = "",
    val source: String = "",
    val title: String = "",
    val sourceLink: String = "",
    val imgUrl: String = "",
    val description: String = "",
    val link: String = "",
    val feedDate: Long = 0,
    val coins: List<NewsCoinsItem> = emptyList()
)

@Serializable
data class NewsCoinsItem(
    val coinKeyWords: String = "",
    val coinPercent: Double = 0.0,
    val coinTitleKeyWords: String = "",
)