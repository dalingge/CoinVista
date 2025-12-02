package com.dalingge.coinvista.core.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class MarketsCap(
    val marketCap: Long = 0,
    val volume: Long = 0,
    val btcDominance: Double = 0.0,
    val marketCapChange: Double = 0.0,
    val volumeChange: Double = 0.0,
    val btcDominanceChange: Double = 0.0,
)

@Serializable
data class MarketsCoins(
    val id: String = "",
    val icon: String = "",
    val name: String = "",
    val symbol: String = "",
    val rank: String = "",
    val price: Double = 0.0,
    val priceBtc: Double = 0.0,
    val volume: Double = 0.0,
    val marketCap: Double = 0.0,
    val availableSupply: Double = 0.0,
    val totalSupply: Double = 0.0,
    val fullyDilutedValuation: Double = 0.0,
    val priceChange1h: Double = 0.0,
    val priceChange1d: Double = 0.0,
    val priceChange1w: Double = 0.0,
)

@Serializable
data class TickersExchanges(
    val id: String = "",
    val icon: String = "",
    val name: String = "",
    val rank: String = "",
    val change24h: Double = 0.0,
    val url: String = "",
    val volume24h: Double = 0.0,
    val volume7d: Double = 0.0,
    val volume1m: Double = 0.0,
)



