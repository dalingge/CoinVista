package com.dalingge.coinvista.core.model.entity

import kotlinx.serialization.Serializable

/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2025/12/2  17:31
 */
@Serializable
data class NFTsTrending(
    val rank : Int = 0,
    val address: String = "",
    val bannerImg: String = "",
    val blockchain: String = "",
    val description: String = "",
    val img: String = "",
    val name: String = "",
    val floorPriceMc : Double = 0.0,
    val floorPriceUsd: Double = 0.0,
    val volumeMc24h: Double = 0.0,
    val floorPriceChange24h: Double = 0.0,
    val verified : Boolean = false,
)
