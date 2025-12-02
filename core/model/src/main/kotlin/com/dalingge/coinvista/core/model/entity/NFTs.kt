package com.dalingge.coinvista.core.model.entity

import kotlinx.serialization.Serializable

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/12/2  17:31
 */
@Serializable
data class NFTsTrending(
    val address: String = "",
    val bannerImg: String = "",
    val blockchain: String = "",
    val description: String = "",
    val img: String = "",
    val name: String = "",
//    val volume24h: Double = 0.0,
//    val volume7d: Double = 0.0,
//    val volume1m: Double = 0.0,
)
